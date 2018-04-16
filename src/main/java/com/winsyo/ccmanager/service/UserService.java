package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.config.JwtUser;
import com.winsyo.ccmanager.domain.Role;
import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import com.winsyo.ccmanager.domain.enumerate.UserType;
import com.winsyo.ccmanager.dto.CreateUserDto;
import com.winsyo.ccmanager.dto.TreeDto;
import com.winsyo.ccmanager.dto.TreeNodeDto;
import com.winsyo.ccmanager.exception.EntityNotFoundException;
import com.winsyo.ccmanager.repository.RoleRepository;
import com.winsyo.ccmanager.repository.UserRepository;
import com.winsyo.ccmanager.util.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  public UserService(UserRepository userRepository, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public Optional<User> findByLoginName(String loginName) {
    return userRepository.findByLoginName(loginName);
  }

  public User findById(String id) {
    return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
  }

  public List<User> findUsersByParentId(String parentId) {
    return userRepository.findUsersByParentId(parentId);
  }

  public User getSystemAdministrator() {
    return userRepository.findByType(UserType.ADMIN).orElseThrow(() -> new EntityNotFoundException("未找到该用户"));
  }

  public User getCurrentUserInfo() {
    JwtUser jwtUser = Utils.getCurrentUser();
    User user = userRepository.findByLoginName(jwtUser.getUsername()).orElseThrow(() -> new EntityNotFoundException("未找到该用户"));
    return user;
  }

  public User getPlatformAdministrator() {
    User user = userRepository.findByType(UserType.PLATFORM).orElseThrow(() -> new EntityNotFoundException("未找到该用户"));
    return user;
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

  public boolean checkLoginNameExist(String username) {
    return findByLoginName(username).isPresent();
  }

  @Transactional
  public void createUser(CreateUserDto dto) {
    User user = new User();
    Role agent = roleRepository.findByRole("AGENT");
    user.setRoles(new LinkedList<>(Arrays.asList(agent)));
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    user.setPassword(encoder.encode(dto.getPassword()));
    user.setIdentityCard(dto.getIdCard());
    user.setInviteCode(Utils.getInviteCode(dto.getPhone().substring(dto.getPhone().length() - 4, dto.getPhone().length())));
    user.setLoginName(dto.getLoginName());
    user.setName(dto.getName());
    user.setType(UserType.AGENT);
    user.setPhone(dto.getPhone());
    try {
      User parent = findById(dto.getParentId());

      user.setParentId(parent.getId());
      user.setTopUserId(parent.getTopUserId());
      user.setUserType(parent.getUserType() + 1);
    } catch (EntityNotFoundException e) {
      User currentUser = getCurrentUserInfo();
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

  }

  @Transactional
  public void modifyUser(CreateUserDto dto) {

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
}
