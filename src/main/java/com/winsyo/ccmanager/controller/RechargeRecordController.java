package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.dto.RechargeRecordDto;
import com.winsyo.ccmanager.service.RechargeRecordService;
import com.winsyo.ccmanager.util.Utils;
import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户充值Controller
 */
@RestController
@RequestMapping(value = "/recharge")
public class RechargeRecordController {

  private RechargeRecordService rechargeRecordService;

  public RechargeRecordController(RechargeRecordService rechargeRecordService) {
    this.rechargeRecordService = rechargeRecordService;
  }

  /**
   * 添加用户充值记录
   * @param dto
   * @return
   */
  @PostMapping(value = "addRechargeRecord")
  public ResponseEntity addRechargeRecord(@RequestBody RechargeRecordDto dto) {
    User currentUser = Utils.getCurrentUser();
    rechargeRecordService.addRechargeRecord(currentUser.getId(), dto.getMoney());
    return ok(true);
  }

  /**
   * 查询平台充值记录
   * @return
   */
  @GetMapping(value = "getPlatformRecharge")
  public ResponseEntity getPlatformRecharge() {
    BigDecimal recharge = rechargeRecordService.getPlatformRecharge();
    return ok(recharge);
  }

}
