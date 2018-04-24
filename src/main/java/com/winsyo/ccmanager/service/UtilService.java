package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.RechargeRecord;
import com.winsyo.ccmanager.dto.RechargeRecordDto;
import com.winsyo.ccmanager.repository.MessageinfoRepository;
import com.winsyo.ccmanager.repository.RechargeRecordRepository;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UtilService {

  private RechargeRecordRepository rechargeRecordRepository;

  private MessageinfoRepository messageinfoRepository;

  public UtilService(RechargeRecordRepository rechargeRecordRepository, MessageinfoRepository messageinfoRepository) {
    this.rechargeRecordRepository = rechargeRecordRepository;
    this.messageinfoRepository = messageinfoRepository;

  }

  @Transactional
  public void addRechargeRecord(RechargeRecordDto rechargeRecordDto) {
    RechargeRecord rechargeRecord = new RechargeRecord();
    rechargeRecord.setMoney(rechargeRecordDto.getMoney());
    rechargeRecord.setUserId(rechargeRecordDto.getUserId());
    rechargeRecord.setTime(LocalDateTime.now());

    rechargeRecordRepository.save(rechargeRecord);
  }

}
