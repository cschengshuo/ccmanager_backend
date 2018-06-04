package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.AppUser;
import com.winsyo.ccmanager.domain.TradingRecord;
import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.domain.enumerate.PayWayTag;
import com.winsyo.ccmanager.repository.TradingRecordRepository;
import com.winsyo.ccmanager.util.Utils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

  /**
   * 查询交易记录
   */
  public Page<TradingRecord> findAll(String cardNo, String userName, PayWayTag payWayTag, Pageable pageable) {
    String agentId = Utils.getCurrentUser().getId();
    List<String> userIds = userService.getAllChildren(agentId).stream().map(User::getId).collect(Collectors.toList());
    userIds.add(agentId);

    Specification<TradingRecord> specification = (Specification<TradingRecord>) (root, query, builder) -> {
      List<Predicate> list = new ArrayList<>();
      Join<TradingRecord, AppUser> appUserJoin = root.join("appUser", JoinType.LEFT);
      Join<AppUser, User> agentJoin = appUserJoin.join("agent", JoinType.LEFT);
      list.add(builder.and(agentJoin.get("id").as(String.class).in(userIds)));
      if (StringUtils.isNotEmpty(userName)) {
        list.add(builder.like(appUserJoin.get("name").as(String.class), userName + '%'));
      }
      if (StringUtils.isNotEmpty(cardNo)) {
        list.add(builder.like(root.get("cardNo").as(String.class), cardNo + '%'));
      }
      if (payWayTag != null) {
        list.add(builder.equal(root.get("payWayTAG").as(PayWayTag.class), payWayTag));
      }
      return builder.and(list.toArray(new Predicate[0]));
    };
    return tradingRecordRepository.findAll(specification, pageable);
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
    List<PayWayTag> payWayTagList = getPayWayTagList();

    Specification<TradingRecord> specification = (Specification<TradingRecord>) (root, query, builder) -> {
      List<Predicate> list = new ArrayList<>();
      Join<TradingRecord, AppUser> appUserJoin = root.join("appUser", JoinType.LEFT);
      Join<AppUser, User> agentJoin = appUserJoin.join("agent", JoinType.LEFT);
      list.add(builder.equal(agentJoin.get("id").as(String.class), agentId));
      list.add(builder.and(root.get("payWayTAG").as(PayWayTag.class).in(payWayTagList)));
      list.add(builder.between(root.get("time").as(LocalDateTime.class), start, end));
      return builder.and(list.toArray(new Predicate[0]));
    };

    List<TradingRecord> records = tradingRecordRepository.findAll(specification);
    return filterOk(records);

  }

  public List<TradingRecord> findTradingRecordsByTime(List<String> agentIds, LocalDateTime start, LocalDateTime end) {
    List<PayWayTag> payWayTagList = getPayWayTagList();

    Specification<TradingRecord> specification = (Specification<TradingRecord>) (root, query, builder) -> {
      List<Predicate> list = new ArrayList<>();
      Join<TradingRecord, AppUser> appUserJoin = root.join("appUser", JoinType.LEFT);
      Join<AppUser, User> agentJoin = appUserJoin.join("agent", JoinType.LEFT);
      list.add(builder.and(agentJoin.get("id").as(String.class).in(agentIds)));
      list.add(builder.and(root.get("payWayTAG").as(PayWayTag.class).in(payWayTagList)));
      list.add(builder.between(root.get("time").as(LocalDateTime.class), start, end));
      return builder.and(list.toArray(new Predicate[0]));
    };

    List<TradingRecord> records = tradingRecordRepository.findAll(specification);
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
