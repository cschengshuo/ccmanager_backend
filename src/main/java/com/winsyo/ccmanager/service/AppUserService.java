package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.AppUser;
import com.winsyo.ccmanager.domain.enumerate.PayWayTag;
import com.winsyo.ccmanager.dto.query.AppUserQueryDto;
import com.winsyo.ccmanager.dto.response.AppUserDto;
import com.winsyo.ccmanager.exception.EntityNotFoundException;
import com.winsyo.ccmanager.repository.AppUserRepository;
import com.winsyo.ccmanager.repository.TradingRecordRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

  private AppUserRepository appUserRepository;
  private TradingRecordRepository tradingRecordRepository;

  public AppUserService(AppUserRepository appUserRepository, TradingRecordRepository tradingRecordRepository) {
    this.appUserRepository = appUserRepository;
    this.tradingRecordRepository = tradingRecordRepository;
  }

  public AppUser findById(String id) {
    return appUserRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("未找到该手机用户", id));
  }

  public Page<AppUserDto> findAppUsers(List<String> userIds, Pageable pagination) {
    Page<AppUserQueryDto> appUsers = appUserRepository.findAppUsers(userIds, pagination);
    Page<AppUserDto> results = appUsers.map(appUserQueryDto -> new AppUserDto(appUserQueryDto));
    return results;
  }

  public BigDecimal getAppUserWithdrawSumMoney() {
    return appUserRepository.findAppUserWithdrawSumMoney();
  }

  public BigDecimal getAppUserHasWithdrawedSumMoney() {
    return tradingRecordRepository.findAppUserHasWithdrawedSumMoney(PayWayTag.WITHDRAW, true);
  }

  public AppUser findByIDNumber(String idNumber) {
    return appUserRepository.findByIdNumber(idNumber).orElseThrow(() -> new EntityNotFoundException("未找到该手机用户", idNumber));
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


}
