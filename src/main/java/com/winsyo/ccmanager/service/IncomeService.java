package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.AppUser;
import com.winsyo.ccmanager.domain.Channel;
import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import com.winsyo.ccmanager.domain.UserFee;
import com.winsyo.ccmanager.domain.TradingRecord;
import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.domain.UserAccount;
import com.winsyo.ccmanager.exception.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeService {

  private AppUserService appUserService;
  private UserService userService;
  private UserAccountService userAccountService;
  private ChannelService channelService;
  private TradingRecordService tradingRecordService;
  private UserFeeService userFeeService;

  @Autowired
  public IncomeService(AppUserService appUserService, UserService userService, UserAccountService userAccountService, ChannelService channelService,
      TradingRecordService tradingRecordService, UserFeeService userFeeService) {
    this.appUserService = appUserService;
    this.userService = userService;
    this.userAccountService = userAccountService;
    this.channelService = channelService;
    this.tradingRecordService = tradingRecordService;
    this.userFeeService = userFeeService;
  }

  public void calcIncome() {
    List<TradingRecord> records = tradingRecordService.listUnsettleTradingRecords();
    if (CollectionUtils.isEmpty(records)) {
      return;
    }

    for (TradingRecord record : records) {
      BigDecimal money = new BigDecimal(Double.toString(record.getMoney()));
      AppUser appUser = appUserService.findById(record.getUserId());
      if (appUser == null || StringUtils.isEmpty(appUser.getAgentId())) {
        continue;
      }
      ChannelType type = record.getPayWayTAG();
      if (type == null) {
        continue;
      }
      List<User> parents = this.userService.getParentQueue(appUser.getAgentId());

      User admin = this.userService.getSystemAdministrator();
      UserAccount adminAccount = userAccountService.findByUserId(admin.getId());

      BigDecimal adminIncome = this.calculateAdminIncome(money, type);
      adminAccount.setPreSettlement(adminAccount.getPreSettlement().add(adminIncome));
      userAccountService.save(adminAccount);

      BigDecimal income = this.calculatePlatformIncome(money, type);
      for (User user : parents) {
        try {
          UserAccount userAccount = userAccountService.findByUserId(user.getId());
          userAccount.setPreSettlement(userAccount.getPreSettlement().add(income));
          userAccountService.save(userAccount);
        } catch (EntityNotFoundException e) {
          UserAccount newAccount = new UserAccount();
          newAccount.setId(UUID.randomUUID().toString());
          newAccount.setBalance(new BigDecimal("0.00"));
          newAccount.setPreSettlement(income);
          newAccount.setUserId(user.getId());
          userAccountService.save(newAccount);

        }
        UserFee fee = null;
        try {
          fee = userFeeService.findByUserIdAndChannelType(user.getId(), type);
        } catch (EntityNotFoundException e) {
          fee = userFeeService.createUserFee(user.getId(), type);
        }
        income = income.multiply(fee.getValue()).setScale(2, RoundingMode.UP);
      }
      record.setSettlementStatus(true);
      tradingRecordService.save(record);
    }
  }

  private BigDecimal calculateAdminIncome(BigDecimal amount, ChannelType type) {
    Channel channel = this.channelService.findByChannelType(type);
    BigDecimal incomeRate = channel.getPlatformFeeRate().subtract(channel.getCostFeeRate());
    BigDecimal incomeFee = channel.getPlatformFee().subtract(channel.getCostFee());
    BigDecimal income = amount.multiply(incomeRate).add(incomeFee);
    return income.setScale(2, RoundingMode.UP);
  }

  private BigDecimal calculatePlatformIncome(BigDecimal amount, ChannelType type) {
    Channel channel = this.channelService.findByChannelType(type);
    BigDecimal incomeRate = channel.getFeeRate().subtract(channel.getPlatformFeeRate());
    BigDecimal incomeFee = channel.getFee().subtract(channel.getPlatformFee());
    BigDecimal income = amount.multiply(incomeRate).add(incomeFee);
    return income.setScale(2, RoundingMode.UP);
  }


}
