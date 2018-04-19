package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.TradingRecord;
import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import com.winsyo.ccmanager.dto.TradingRecordQueryDto;
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

  public List<TradingRecord> findTradingRecordsByTime(String agentId, LocalDateTime start, LocalDateTime end) {
    return tradingRecordRepository.findTradingRecordsByTime(agentId, start, end);
  }

  public List<TradingRecord> findTradingRecordsByTime(List<String> agentIds, LocalDateTime start, LocalDateTime end) {
    return tradingRecordRepository.findTradingRecordsByTime(agentIds, start, end);
  }

}
