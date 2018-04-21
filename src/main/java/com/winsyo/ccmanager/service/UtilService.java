package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.Messageinfo;
import com.winsyo.ccmanager.domain.RechargeRecord;
import com.winsyo.ccmanager.dto.RechargeRecordDto;
import com.winsyo.ccmanager.repository.MessageinfoRepository;
import com.winsyo.ccmanager.repository.RechargeRecordRepository;
import com.winsyo.ccmanager.util.umengpush.UmengPush;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class UtilService {

  private RechargeRecordRepository rechargeRecordRepository;

  private MessageinfoRepository messageinfoRepository;

  public UtilService(RechargeRecordRepository rechargeRecordRepository, MessageinfoRepository messageinfoRepository) {
    this.rechargeRecordRepository = rechargeRecordRepository;
    this.messageinfoRepository = messageinfoRepository;

  }

  public void addRechargeRecord(RechargeRecordDto rechargeRecordDto) {
    RechargeRecord rechargeRecord = new RechargeRecord();
    rechargeRecord.setMoney(rechargeRecordDto.getMoney());
    rechargeRecord.setUserId(rechargeRecordDto.getUserId());
    rechargeRecord.setTime(LocalDateTime.now());

    rechargeRecordRepository.save(rechargeRecord);
  }

  public void sendAnnouncement(String text) {
    Messageinfo messageinfo = new Messageinfo();
    messageinfo.setText(text);
    messageinfo.setType("0");
    messageinfo.setCreatetime(LocalDateTime.now());

    messageinfoRepository.save(messageinfo);

    UmengPush.sendAndroidBroadcast(text);
    UmengPush.sendIOSBroadcast(text);

  }

}
