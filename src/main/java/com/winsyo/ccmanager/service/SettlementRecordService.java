package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.repository.SettlementRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class SettlementRecordService {

  private SettlementRecordRepository settlementRecordRepository;

  public SettlementRecordService(SettlementRecordRepository settlementRecordRepository) {
    this.settlementRecordRepository = settlementRecordRepository;
  }
}
