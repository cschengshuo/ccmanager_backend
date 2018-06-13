package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.RechargeRecord;
import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.repository.RechargeRecordRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * 充值记录Services
 */
@Service
public class RechargeRecordService {

  private RechargeRecordRepository rechargeRecordRepository;
  private UserService userService;

  public RechargeRecordService(RechargeRecordRepository rechargeRecordRepository, UserService userService) {
    this.rechargeRecordRepository = rechargeRecordRepository;
    this.userService = userService;
  }

  /**
   * 添加充值记录
   *
   * @param userId 代理ID
   * @param money 充值金额
   */
  @Transactional
  public void addRechargeRecord(String userId, BigDecimal money) {
    RechargeRecord rechargeRecord = new RechargeRecord();
    rechargeRecord.setMoney(money);
    rechargeRecord.setUserId(userId);
    rechargeRecord.setTime(LocalDateTime.now());

    rechargeRecordRepository.save(rechargeRecord);
  }

  /**
   * 获取平台充值记录
   */
  public BigDecimal getPlatformRecharge() {
    User platform = userService.getPlatformAdministrator();
    List<RechargeRecord> records = rechargeRecordRepository.findByUserId(platform.getId());
    BigDecimal result = records.stream().map(rechargeRecord -> rechargeRecord.getMoney()).reduce(BigDecimal::add).orElse(new BigDecimal("0"));
    return result;
  }
}
