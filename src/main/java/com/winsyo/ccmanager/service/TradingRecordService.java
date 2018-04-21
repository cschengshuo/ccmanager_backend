package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.TradingRecord;
import com.winsyo.ccmanager.domain.enumerate.ChannelType;
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

  public Page<TradingRecordQueryDto> findAll(Pageable pageable, String cardNo) {
    String agentId = Utils.getCurrentUser().getId();
    List<String> userIds = userService.getAllChildren(agentId).stream().map(user -> user.getId()).collect(Collectors.toList());
    userIds.add(agentId);
    cardNo = cardNo + "%";

    return tradingRecordRepository.findTradingRecords(userIds, cardNo, pageable);
  }

  public Page<TradingRecordQueryDto> findWithDraw(Pageable pagable) {
    String agentId = Utils.getCurrentUser().getId();
    List<String> userIds = userService.getAllChildren(agentId).stream().map(user -> user.getId()).collect(Collectors.toList());
    userIds.add(agentId);

    return tradingRecordRepository.findTradingRecordsByPayWayTag(userIds, PayWayTag.WITHDRAW, pagable);
  }

  public List<TradingRecord> listUnsettleTradingRecords() {
    List<PayWayTag> list = new ArrayList<>();
    list.add(PayWayTag.PLAN);
    list.add(PayWayTag.C);
    list.add(PayWayTag.D);
    list.add(PayWayTag.E);
    list.add(PayWayTag.F);

    List<TradingRecord> records = tradingRecordRepository.findByPayWayTAGIsInAndSettlementStatusAndOkAndType(list, false, true, "0");
    return records;
  }

  @Transactional
  public TradingRecord save(TradingRecord record) {
    return tradingRecordRepository.save(record);
  }

  public List<TradingRecord> findTradingRecordsByTime(String agentId, LocalDateTime start, LocalDateTime end) {
    return tradingRecordRepository.findTradingRecordsByTime(agentId, start, end).stream().filter(this::isValidChannelType).collect(Collectors.toList());
  }

  public List<TradingRecord> findTradingRecordsByTime(List<String> agentIds, LocalDateTime start, LocalDateTime end) {
    return tradingRecordRepository.findTradingRecordsByTime(agentIds, start, end).stream().filter(this::isValidChannelType).collect(Collectors.toList());
  }

  public boolean isValidChannelType(TradingRecord tradingRecord) {
    List<ChannelType> types = new ArrayList<>();
    types.add(ChannelType.PLAN);
    types.add(ChannelType.C);
    types.add(ChannelType.D);
    types.add(ChannelType.E);
    types.add(ChannelType.F);

    return types.contains(tradingRecord.getPayWayTAG());
  }

}
