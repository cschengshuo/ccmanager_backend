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

/**
 * 手机用户Controller
 */
@RestController
@RequestMapping(value = "/app_user")
public class AppUserController {

  private AppUserService appUserService;
  private UserService userService;

  public AppUserController(AppUserService appUserService, UserService userService) {
    this.appUserService = appUserService;
    this.userService = userService;
  }

  /**
   * 根据代理ID查询可见的手机用户
   * @param agentId 代理ID
   * @param username 查询条件 手机用户姓名
   * @param mobile 查询条件 手机号
   * @param idCard 查询条件 身份证
   * @param page 分页位置
   * @param size 分页大小
   * @return
   */
  @GetMapping(value = "findAppUsersByAgentId")
  public ResponseEntity findAppUsersByAgentId(String agentId, @RequestParam(required = false) String username, @RequestParam(required = false) String mobile,
      @RequestParam(required = false) String idCard, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
    PageRequest pagination = PageRequest.of(page, size);
    if (StringUtils.isEmpty(agentId)) {
      agentId = Utils.getCurrentUser().getId();
    }

    List<String> userIds = userService.getAllChildren(agentId).stream().map(user -> user.getId()).collect(Collectors.toList());
    userIds.add(agentId);

    Page<AppUserDto> appUsers = appUserService.findAppUsers(userIds, username, mobile, idCard, pagination);
    return ok(appUsers);
  }

  /**
   * 获取所有手机用户待提现金额
   * @return
   */
  @GetMapping(value = "getAppUserWithdrawSum")
  public ResponseEntity getAppUserWithdrawSum() {
    BigDecimal sumMoney = appUserService.getAppUserWithdrawSumMoney();
    return ok(sumMoney);
  }

  /**
   * 获取所有手机用户已提现金额
   * @return
   */
  @GetMapping(value = "getAppUserHasWithdrawedSum")
  public ResponseEntity getAppUserWithdrawedSum() {
    BigDecimal sumMoney = appUserService.getAppUserHasWithdrawedSumMoney();
    return ok(sumMoney);
  }

}
