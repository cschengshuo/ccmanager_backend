package com.winsyo.ccmanager.controller;

import com.winsyo.ccmanager.domain.AppUser;
import com.winsyo.ccmanager.service.AppUserService;
import com.winsyo.ccmanager.util.Utils;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
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
    if (StringUtils.isEmpty(agentId)) {
      agentId = Utils.getCurrentUser().getId();
    }
    List<AppUser> appUsers = appUserService.findAppUsers(agentId);
    return appUsers;
  }

}
