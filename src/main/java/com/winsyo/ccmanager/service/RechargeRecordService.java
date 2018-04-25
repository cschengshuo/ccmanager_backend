package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.RechargeRecord;
import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.exception.OperationFailureException;
import com.winsyo.ccmanager.repository.RechargeRecordRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RechargeRecordService {

  private RechargeRecordRepository rechargeRecordRepository;
  private UserService userService;

  public RechargeRecordService(RechargeRecordRepository rechargeRecordRepository, UserService userService) {
    this.rechargeRecordRepository = rechargeRecordRepository;
    this.userService = userService;
  }

  @Transactional
  public void addRechargeRecord(String userId, BigDecimal money) {
    RechargeRecord rechargeRecord = new RechargeRecord();
    rechargeRecord.setMoney(money);
    rechargeRecord.setUserId(userId);
    rechargeRecord.setTime(LocalDateTime.now());

    rechargeRecordRepository.save(rechargeRecord);
  }

  public BigDecimal getPlatformRecharge() {
    User platform = userService.getPlatformAdministrator();
    List<RechargeRecord> records = rechargeRecordRepository.findByUserId(platform.getId());
    BigDecimal result = records.stream().map(rechargeRecord -> rechargeRecord.getMoney()).reduce(BigDecimal::add).orElseThrow(() -> new OperationFailureException("空"));
    return result;
  }
}
