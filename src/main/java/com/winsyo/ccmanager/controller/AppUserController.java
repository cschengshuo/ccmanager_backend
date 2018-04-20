package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.math.BigDecimal;

import com.winsyo.ccmanager.dto.AppUserDto;
import com.winsyo.ccmanager.service.AppUserService;
import com.winsyo.ccmanager.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public ResponseEntity findAppUsersByAgentId(String agentId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
    PageRequest pagination = PageRequest.of(page, size);
    if (StringUtils.isEmpty(agentId)) {
      agentId = Utils.getCurrentUser().getId();
    }
    Page<AppUserDto> appUsers = appUserService.findAppUsers(agentId, pagination);
    return ok(appUsers);
  }

  
  @GetMapping(value = "getAppUserWithdrawSum")
  public ResponseEntity getAppUserWithdrawSum() {
	  BigDecimal sumMoney = appUserService.getAppUserWithdrawSumMoney();
    return ok(sumMoney);
  }
  
  
  
}
