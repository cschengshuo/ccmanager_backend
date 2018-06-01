package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.TradingRecord;
import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.domain.enumerate.PayWayTag;
import com.winsyo.ccmanager.dto.query.TradingRecordQueryDto;
import com.winsyo.ccmanager.repository.TradingRecordRepository;
import com.winsyo.ccmanager.util.Utils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 交易记录服务
 */
@Service
public class TradingRecordService {

  private TradingRecordRepository tradingRecordRepository;
  private AppUserService appUserService;
  private UserService userService;

  public TradingRecordService(TradingRecordRepository tradingRecordRepository, AppUserService appUserService, UserService userService) {
    this.tradingRecordRepository = tradingRecordRepository;
    this.appUserService = appUserService;
    this.userService = userService;
  }

  public Page<TradingRecordQueryDto> findAll(Pageable pageable, String cardNo,String  userName) {
    String agentId = Utils.getCurrentUser().getId();
    List<String> userIds = userService.getAllChildren(agentId).stream().map(User::getId).collect(Collectors.toList());
    userIds.add(agentId);
    cardNo = cardNo + "%";
    userName ="%" + userName + "%";

    return tradingRecordRepository.findTradingRecords(userIds, cardNo,userName, pageable);
  }

  public Page<TradingRecordQueryDto> findWithDraw(Pageable pagable) {
    String agentId = Utils.getCurrentUser().getId();
    List<String> userIds = userService.getAllChildren(agentId).stream().map(User::getId).collect(Collectors.toList());
    userIds.add(agentId);

    return tradingRecordRepository.findTradingRecordsByPayWayTag(userIds, PayWayTag.WITHDRAW, pagable);
  }

  public List<TradingRecord> listUnsettleTradingRecords() {
    List<PayWayTag> list = getPayWayTagList();

    List<TradingRecord> records = tradingRecordRepository.findByPayWayTAGIsInAndSettlementStatusAndType(list, false, "0");
    return filterOk(records);

  }

  @Transactional
  public TradingRecord save(TradingRecord record) {
    return tradingRecordRepository.save(record);
  }

  public List<TradingRecord> findTradingRecordsByTime(String agentId, LocalDateTime start, LocalDateTime end) {
    List<PayWayTag> list = getPayWayTagList();

    List<TradingRecord> records = tradingRecordRepository.findTradingRecordsByTime(agentId, start, end, list);
    return filterOk(records);

  }

  public List<TradingRecord> findTradingRecordsByTime(List<String> agentIds, LocalDateTime start, LocalDateTime end) {
    List<PayWayTag> list = getPayWayTagList();

    List<TradingRecord> records = tradingRecordRepository.findTradingRecordsByTime(agentIds, start, end, list);
    return filterOk(records);

  }

  public List<TradingRecord> findTradingRecordsByTime(LocalDateTime start, LocalDateTime end) {
    List<TradingRecord> records = tradingRecordRepository.findByPayWayTAGIsInAndTypeAndTimeBetween(getPayWayTagList(), "0", start, end);
    return filterOk(records);
  }

  private List<TradingRecord> filterOk(List<TradingRecord> records) {
    return records.stream().filter(TradingRecord::isOk).collect(Collectors.toList());
  }

  private List<PayWayTag> getPayWayTagList() {
    List<PayWayTag> list = new ArrayList<>();
    list.add(PayWayTag.PLAN);
    list.add(PayWayTag.C);
    list.add(PayWayTag.D);
    list.add(PayWayTag.E);
    list.add(PayWayTag.F);

    return list;
  }

}
