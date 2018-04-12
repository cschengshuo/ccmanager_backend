package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.config.JwtUser;
import com.winsyo.ccmanager.domain.Role;
import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.domain.UserType;
import com.winsyo.ccmanager.dto.TreeDto;
import com.winsyo.ccmanager.dto.TreeNodeDto;
import com.winsyo.ccmanager.exception.EntityNotFoundException;
import com.winsyo.ccmanager.repository.RoleRepository;
import com.winsyo.ccmanager.repository.UserRepository;
import com.winsyo.ccmanager.util.SecurityUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    JwtUser jwtUser = SecurityUtils.getCurrentUser();
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

  @Transactional
  public void testJob() {
    User thinkgem = userRepository.findByLoginName("thinkgem").orElseThrow(() -> {
      return new EntityNotFoundException("123");
    });
    thinkgem.setCurrentTime(System.currentTimeMillis());
    userRepository.save(thinkgem);
  }

  @Transactional
  public void testJob2() {
    User thinkgem = userRepository.findByLoginName("admin666").orElseThrow(() -> {
      return new EntityNotFoundException("123");
    });
    thinkgem.setCurrentTime(System.currentTimeMillis());
    userRepository.save(thinkgem);
  }

  @Transactional
  public void initUserRole() {
    Role admin = roleRepository.findByRole("ADMIN");
    LinkedList<Role> adminList = new LinkedList<>(Arrays.asList(admin));

    Role platform = roleRepository.findByRole("PLATFORM");
    LinkedList<Role> platformList = new LinkedList<>(Arrays.asList(platform));

    Role agent = roleRepository.findByRole("AGENT");
    LinkedList<Role> agentList = new LinkedList<>(Arrays.asList(agent));

    List<User> all = userRepository.findAll();
    all.forEach((user) -> {
      switch (user.getType()) {
        case ADMIN:
          user.setRoles(adminList);
          break;

        case PLATFORM:
          user.setRoles(platformList);
          break;

        case VIRTUAL:
        case AGENT:
          user.setRoles(agentList);
          break;
      }
      userRepository.save(user);
    });
  }
}
