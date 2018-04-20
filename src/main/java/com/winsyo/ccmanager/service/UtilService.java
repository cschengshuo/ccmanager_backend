package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.RechargeRecord;
import com.winsyo.ccmanager.dto.RechargeRecordDto;
import com.winsyo.ccmanager.repository.RechargeRecordRepository;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;


@Service
public class UtilService {

  private RechargeRecordRepository rechargeRecordRepository;
 

  public UtilService(RechargeRecordRepository rechargeRecordRepository) {
    this.rechargeRecordRepository = rechargeRecordRepository;
   
  }

  public void addRechargeRecord(RechargeRecordDto rechargeRecordDto) {
	  RechargeRecord rechargeRecord = new RechargeRecord();
	  rechargeRecord.setMoney(rechargeRecordDto.getMoney());
	  rechargeRecord.setUserId(rechargeRecordDto.getUserId());
	  rechargeRecord.setTime(LocalDateTime.now());
	  
	  rechargeRecordRepository.save(rechargeRecord);
  }
  


}
