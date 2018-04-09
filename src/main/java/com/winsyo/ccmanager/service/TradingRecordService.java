package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.TradingRecord;
import com.winsyo.ccmanager.repository.TradingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TradingRecordService {

  private TradingRecordRepository tradingRecordRepository;

  @Autowired
  public TradingRecordService(TradingRecordRepository tradingRecordRepository) {
    this.tradingRecordRepository = tradingRecordRepository;
  }

  public Page<TradingRecord> findAll(Pageable pageable) {
    Page<TradingRecord> all = tradingRecordRepository.findAll(pageable);
    return all;
  }
}
