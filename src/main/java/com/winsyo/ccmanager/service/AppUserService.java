package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.AppUser;
import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.domain.enumerate.PayWayTag;
import com.winsyo.ccmanager.dto.response.AppUserDto;
import com.winsyo.ccmanager.exception.EntityNotFoundException;
import com.winsyo.ccmanager.external.helibao.HlbPayService;
import com.winsyo.ccmanager.repository.AppUserRepository;
import com.winsyo.ccmanager.repository.TradingRecordRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private AppUserRepository appUserRepository;
  private TradingRecordRepository tradingRecordRepository;
  private HlbPayService hlbPayService;

  public AppUserService(AppUserRepository appUserRepository, TradingRecordRepository tradingRecordRepository, HlbPayService hlbPayService) {
    this.appUserRepository = appUserRepository;
    this.tradingRecordRepository = tradingRecordRepository;
    this.hlbPayService = hlbPayService;
  }

  public AppUser findById(String id) {
    return appUserRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("未找到该手机用户", id));
  }

  /**
   * 查询手机用户列表
   *
   * @param userIds 归属代理ID范围
   */
  public Page<AppUserDto> findAppUsers(List<String> userIds, String username, String mobile, String idCard, Pageable pagination) {
    Specification<AppUser> appUserSpecification = (Specification<AppUser>) (root, query, builder) -> {
      List<Predicate> list = new ArrayList<>();
      Join<AppUser, User> userJoin = root.join("agent", JoinType.LEFT);
      list.add(builder.and(userJoin.get("id").as(String.class).in(userIds)));
      if (StringUtils.isNotEmpty(username)) {
        list.add(builder.like(root.get("name").as(String.class), username + '%'));
      }
      if (StringUtils.isNotEmpty(mobile)) {
        list.add(builder.like(root.get("contactPhone").as(String.class), mobile + '%'));
      }
      if (StringUtils.isNotEmpty(idCard)) {
        list.add(builder.like(root.get("idNumber").as(String.class), idCard + '%'));
      }
      return builder.and(list.toArray(new Predicate[0]));
    };
    Page<AppUser> appUsers = appUserRepository.findAll(appUserSpecification, pagination);
    Page<AppUserDto> results = appUsers.map(AppUserDto::new);
    return results;
  }

  public BigDecimal getAppUserWithdrawSumMoney() {
    return appUserRepository.findAppUserWithdrawSumMoney();
  }

  public BigDecimal getAppUserHasWithdrawedSumMoney() {
    return tradingRecordRepository.findAppUserHasWithdrawedSumMoney(PayWayTag.WITHDRAW);
  }

  public AppUser findByContactPhone(String contactPhone) {
    return appUserRepository.findByContactPhone(contactPhone).orElseThrow(() -> new EntityNotFoundException("未找到该手机用户", contactPhone));
  }

  public List<AppUser> findAppUsersByRecommendUserId(String inviteCoded) {
    return appUserRepository.findAppUsersByRecommendUserId(inviteCoded);
  }

  public List<AppUser> findSubAppUsers(String appUserId) {
    AppUser parent = findById(appUserId);
    List<AppUser> children = findAppUsersByRecommendUserId(parent.getUserId());
    List<AppUser> result = new ArrayList<>(children);

    children.forEach(user -> {
      List<AppUser> grandchildren = findSubAppUsers(user.getUserId());
      result.addAll(grandchildren);
    });

    return result;
  }

  @Transactional
  public AppUser save(AppUser appUser) {
    return appUserRepository.save(appUser);
  }

  /**
   * 临时方法 为所有手机用户开通合利宝商户
   */
  public void doCreateMerchant() {
    List<AppUser> all = appUserRepository.findAll();
    all.forEach(appUser -> {
      String contactPhone = appUser.getContactPhone();
      String idNumber = appUser.getIdNumber();

      if (StringUtils.isEmpty(idNumber)) {
        return;
      }

      String merchant = hlbPayService.createMerchant(contactPhone, idNumber);

      if (StringUtils.isEmpty(merchant)) {
        return;
      }
      appUser.setUserNo(merchant);
      save(appUser);
    });

  }

}
