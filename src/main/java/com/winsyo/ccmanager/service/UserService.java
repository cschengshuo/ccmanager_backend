package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.Role;
import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.domain.enumerate.UserType;
import com.winsyo.ccmanager.dto.CreateUserDto;
import com.winsyo.ccmanager.dto.ModifyUserDto;
import com.winsyo.ccmanager.dto.TreeDto;
import com.winsyo.ccmanager.dto.TreeNodeDto;
import com.winsyo.ccmanager.exception.EntityNotFoundException;
import com.winsyo.ccmanager.exception.OperationFailureException;
import com.winsyo.ccmanager.repository.RoleRepository;
import com.winsyo.ccmanager.repository.UserRepository;
import com.winsyo.ccmanager.util.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 系统用户服务
 */
@Service
public class UserService {

  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private UserFeeService userFeeService;

  public UserService(UserRepository userRepository, RoleRepository roleRepository, UserFeeService userFeeService) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.userFeeService = userFeeService;
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public User findByUsername(String username) {
    return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("未找到该用户"));
  }

  public User findById(String id) {
    return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("未找到该用户"));
  }

  public List<User> findUsersByParentId(String parentId) {
    return userRepository.findUsersByParentId(parentId);
  }

  public User getSystemAdministrator() {
    return userRepository.findByType(UserType.ADMIN).orElseThrow(() -> new EntityNotFoundException("未找到系统管理员"));
  }

  public User getPlatformAdministrator() {
    return userRepository.findByType(UserType.PLATFORM).orElseThrow(() -> new EntityNotFoundException("未找到平台管理员"));
  }

  public TreeDto map(User user) {
    long count = userRepository.countByParentId(user.getId());
    if (count > 0) {
      return new TreeNodeDto(user);
    }
    return new TreeDto(user);
  }

  /**
   * 获取父级用户队列
   */
  public List<User> getParentQueue(String id) {
    List<User> users = new ArrayList<>();

    User node = findById(id);
    users.add(node);
    while (!StringUtils.equals(node.getId(), node.getTopUserId())) {
      try {
        node = findById(node.getParentId());
      } catch (EntityNotFoundException e) {
        break;
      }
      users.add(node);
    }
    Collections.reverse(users);
    return users;
  }

  public boolean checkUsernameExist(String username) {
    try {
      findByUsername(username);
    } catch (EntityNotFoundException e) {
      return false;
    }
    return true;
  }

  @Transactional
  public void setPassword(String username, String oldPassword, String password) {
    User user = findByUsername(username);
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    if (encoder.matches(oldPassword, user.getPassword())) {
      user.setPassword(encoder.encode(password));
      userRepository.save(user);
    } else {
      throw new OperationFailureException("原密码输入不正确");
    }
  }

  @Transactional
  public void createAgentUser(CreateUserDto dto) {
    User user = new User();
    Role agent = roleRepository.findByRole("AGENT");
    user.setRoles(new LinkedList<>(Arrays.asList(agent)));
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    user.setPassword(encoder.encode(dto.getPassword()));
    user.setIdentityCard(dto.getIdCard());
    user.setInviteCode(Utils.getInviteCode(dto.getPhone().substring(dto.getPhone().length() - 4, dto.getPhone().length())));
    user.setUsername(dto.getLoginName());
    user.setName(dto.getName());
    user.setType(UserType.AGENT);
    user.setPhone(dto.getPhone());
    user.setAgentAreaCode(dto.getAreaCode());
    try {
      User parent = findById(dto.getParentId());
      user.setParentId(parent.getId());
      user.setTopUserId(parent.getTopUserId());
      user.setUserType(parent.getUserType() + 1);
      user.setParentIds("");
    } catch (EntityNotFoundException e) {
      User currentUser = Utils.getCurrentUser();
      User parent;
      if (currentUser.getType() != UserType.ADMIN) {
        parent = currentUser;
      } else {
        parent = getPlatformAdministrator();
      }

      user.setParentId(parent.getId());
      user.setTopUserId(parent.getTopUserId());
      user.setUserType(parent.getUserType() + 1);
      user.setParentIds("");
    }
    userRepository.save(user);
    userFeeService.createUserFee(dto.getFeeRate(), user.getId());
  }

  @Transactional
  public void modifyUser(ModifyUserDto dto) {
    User user = findById(dto.getId());
    user.setAgentAreaCode(dto.getAreaCode());
    user.setIdentityCard(dto.getIdCard());
    user.setName(dto.getName());
    user.setPhone(dto.getPhone());
    userRepository.save(user);

    userFeeService.removeUserFee(dto.getId());
    userFeeService.createUserFee(dto.getFeeRate(), dto.getId());

  }

  public List<User> findUsers(String name) {
    Specification<User> appUserSpecification = (Specification<User>) (root, query, builder) -> {
      List<Predicate> list = new ArrayList<Predicate>();
      list.add(builder.equal(root.get("type").as(UserType.class), UserType.AGENT));
      if (StringUtils.isNotEmpty(name)) {
        list.add(builder.like(root.get("name").as(String.class), name + '%'));
      }
      Predicate[] p = new Predicate[list.size()];
      return builder.and(list.toArray(p));
    };
    List<User> users = userRepository.findAll(appUserSpecification);
    return users;
  }

  public TreeDto getUserTreeRoot() {
    User user;

    User currentUser = Utils.getCurrentUser();
    if (currentUser.getType() != UserType.ADMIN) {
      user = currentUser;
    } else {
      user = getPlatformAdministrator();
    }

    List<User> users = findUsersByParentId(user.getId());

    if (CollectionUtils.isEmpty(users)) {
      return new TreeDto(user);
    } else {
      TreeNodeDto dto = new TreeNodeDto(user);
      List<TreeDto> dtos = users.stream().map(this::map).collect(Collectors.toList());
      dto.setChildren(dtos);
      dto.setExpand(true);
      return dto;
    }

  }

}
