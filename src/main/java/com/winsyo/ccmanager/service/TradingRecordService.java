package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.TradingRecord;
import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import com.winsyo.ccmanager.repository.TradingRecordRepository;
import java.util.Arrays;
import java.util.List;
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

  public List<TradingRecord> listUnsettleTradingRecords() {
    List<TradingRecord> records = tradingRecordRepository
        .findAllBySettlementStatusAndOkAndPayWayTAGIn(false, true, Arrays.asList(ChannelType.PLAN, ChannelType.C, ChannelType.E, ChannelType.F));
    return records;
  }

  public TradingRecord save(TradingRecord record) {
    return tradingRecordRepository.save(record);
  }

  /**
   * 列出今天的交易记录
   */
  public List<TradingRecord> listTodayTradingRecords() {
    List<TradingRecord> records = tradingRecordRepository.findAll();
    return records;
  }

  /**
   * 列出本月交易记录
   */
  public List<TradingRecord> listMonthTradingRecords() {
    List<TradingRecord> records = tradingRecordRepository.findAll();
    return records;
  }
}
