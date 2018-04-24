package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.winsyo.ccmanager.dto.response.AppUserDto;
import com.winsyo.ccmanager.service.AppUserService;
import com.winsyo.ccmanager.service.UserService;
import com.winsyo.ccmanager.util.Utils;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
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
  private UserService userService;

  public AppUserController(AppUserService appUserService, UserService userService) {
    this.appUserService = appUserService;
    this.userService = userService;
  }

  @GetMapping(value = "findAppUsersByAgentId")
  public ResponseEntity findAppUsersByAgentId(String agentId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
    PageRequest pagination = PageRequest.of(page, size);
    if (StringUtils.isEmpty(agentId)) {
      agentId = Utils.getCurrentUser().getId();
    }

    List<String> userIds = userService.getAllChildren(agentId).stream().map(user -> user.getId()).collect(Collectors.toList());
    userIds.add(agentId);

    Page<AppUserDto> appUsers = appUserService.findAppUsers(userIds, pagination);
    return ok(appUsers);
  }

  @GetMapping(value = "getAppUserWithdrawSum")
  public ResponseEntity getAppUserWithdrawSum() {
    BigDecimal sumMoney = appUserService.getAppUserWithdrawSumMoney();
    return ok(sumMoney);
  }

  @GetMapping(value = "getAppUserHasWithdrawedSum")
  public ResponseEntity getAppUserWithdrawedSum() {
    BigDecimal sumMoney = appUserService.getAppUserHasWithdrawedSumMoney();
    return ok(sumMoney);
  }


}
