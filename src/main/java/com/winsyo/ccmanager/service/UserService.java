package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.Role;
import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.repository.RoleRepository;
import com.winsyo.ccmanager.repository.UserRepository;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

  private UserRepository userRepository;
  private RoleRepository roleRepository;

  @Autowired
  public UserService(UserRepository userRepository, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  public Optional<User> findByLoginName(String loginName) {
    return userRepository.findByLoginName(loginName);
  }

  public Optional<User> findById(String id) {
    return userRepository.findById(id);
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
