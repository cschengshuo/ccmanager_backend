package com.winsyo.ccmanager.controller;

import com.winsyo.ccmanager.domain.AppUser;
import com.winsyo.ccmanager.service.AppUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/app_user")
public class AppUserController {

  private AppUserService appUserService;

  @Autowired
  public AppUserController(AppUserService appUserService) {
    this.appUserService = appUserService;
  }

  @GetMapping(value = "findAppUsersByAgentId")
  public List<AppUser> findAppUsersByAgentId(String agentId) {
    List<AppUser> appUsers = appUserService.findAppUsers(agentId);
    return appUsers;
  }

}
