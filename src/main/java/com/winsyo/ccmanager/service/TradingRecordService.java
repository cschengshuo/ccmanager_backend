package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.TradingRecord;
import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import com.winsyo.ccmanager.repository.TradingRecordRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class TradingRecordService {

  private TradingRecordRepository tradingRecordRepository;
  private AppUserService appUserService;

  public TradingRecordService(TradingRecordRepository tradingRecordRepository, AppUserService appUserService) {
    this.tradingRecordRepository = tradingRecordRepository;
    this.appUserService = appUserService;
  }

  public Page<TradingRecord> findAll(Pageable pageable, String cardNo) {
    Specification<TradingRecord> specification = (Specification<TradingRecord>) (root, query, builder) -> {
      List<Predicate> list = new ArrayList<Predicate>();
      if (StringUtils.isNotEmpty(cardNo)) {
        list.add(builder.like(root.get("cardNo").as(String.class), cardNo + '%'));
      }
      Predicate[] p = new Predicate[list.size()];
      return builder.and(list.toArray(p));
    };
    Page<TradingRecord> all = tradingRecordRepository.findAll(specification, pageable);
    return all;
  }

  public List<TradingRecord> listUnsettleTradingRecords() {
    List<String> list = new ArrayList<>();
    list.add(ChannelType.PLAN.index() + "");
    list.add(ChannelType.C.index() + "");
    list.add(ChannelType.D.index() + "");
    list.add(ChannelType.E.index() + "");
    list.add(ChannelType.F.index() + "");

    List<TradingRecord> records = tradingRecordRepository
        .findByPayWayTAGIsInAndSettlementStatusAndOk(list, false, true);
    return records;
  }

  @Transactional
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
