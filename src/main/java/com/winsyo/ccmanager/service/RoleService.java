package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.Role;
import com.winsyo.ccmanager.repository.RoleRepository;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

  private RoleRepository roleRepository;

  @Autowired
  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Transactional
  public void initRole() {
    Role admin = new Role();
    admin.setId(UUID.randomUUID().toString());
    admin.setName("系统管理员");
    admin.setRole("ADMIN");
    Role platform = new Role();
    platform.setId(UUID.randomUUID().toString());
    platform.setName("平台管理员");
    platform.setRole("PLATFORM");
    Role agent = new Role();
    agent.setId(UUID.randomUUID().toString());
    agent.setName("代理商");
    agent.setRole("AGENT");
    roleRepository.save(admin);
    roleRepository.save(platform);
    roleRepository.save(agent);
  }

}
