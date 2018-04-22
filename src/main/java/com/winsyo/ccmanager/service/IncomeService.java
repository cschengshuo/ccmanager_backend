package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.AppUser;
import com.winsyo.ccmanager.domain.Channel;
import com.winsyo.ccmanager.domain.TradingRecord;
import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.domain.UserAccount;
import com.winsyo.ccmanager.domain.UserFee;
import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import com.winsyo.ccmanager.exception.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
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

  @Transactional
  public void calcIncome() {
    List<TradingRecord> records = tradingRecordService.listUnsettleTradingRecords();
    if (CollectionUtils.isEmpty(records)) {
      return;
    }

    records.forEach(record -> {
      BigDecimal money = record.getMoney();
      AppUser appUser;
      List<User> parents;
      try {
        appUser = appUserService.findById(record.getUserId());
        parents = this.userService.getParentQueue(appUser.getAgentId());
      } catch (EntityNotFoundException e) {
        return;
      }
      ChannelType type = ChannelType.indexOf(record.getPayWayTAG());
      if (type == null) {
        return;
      }

      User admin = this.userService.getSystemAdministrator();
      UserAccount adminAccount = userAccountService.findByUserIdAndType(admin.getId(), type);
      BigDecimal adminIncome = this.calculateAdminIncome(money, type);
      adminAccount.setPreSettlement(adminAccount.getPreSettlement().add(adminIncome));
      userAccountService.save(adminAccount);

      parents.forEach(user -> {
        Pair<UserFee, UserFee> userFeePair;
        try {
          userFeePair = userFeeService.findByUserIdAndChannelType(user.getId(), type);
        } catch (EntityNotFoundException e) {
          userFeePair = userFeeService.createUserFee(user.getId(), type);
        }
        BigDecimal feeRate = userFeePair.getFirst().getValue();
        BigDecimal fee = userFeePair.getSecond().getValue();
        BigDecimal income = money.multiply(feeRate).add(fee).setScale(2, RoundingMode.UP);

        try {
          UserAccount userAccount = userAccountService.findByUserIdAndType(user.getId(), type);
          userAccount.setPreSettlement(userAccount.getPreSettlement().add(income));
          userAccountService.save(userAccount);
        } catch (EntityNotFoundException e) {
          UserAccount newAccount = new UserAccount();
          newAccount.setBalance(new BigDecimal("0.00"));
          newAccount.setPreSettlement(income);
          newAccount.setUserId(user.getId());
          newAccount.setType(type);
          userAccountService.save(newAccount);
        }
      });

      record.setSettlementStatus(true);
      tradingRecordService.save(record);
    });
  }

  public BigDecimal calculateAdminIncome(BigDecimal amount, ChannelType type) {
    Channel channel = this.channelService.findByChannelType(type);
    BigDecimal incomeRate = channel.getPlatformFeeRate().subtract(channel.getCostFeeRate());
    BigDecimal incomeFee = channel.getPlatformFee().subtract(channel.getCostFee());
    BigDecimal income = amount.multiply(incomeRate).add(incomeFee);
    return income.setScale(2, RoundingMode.UP);
  }


  public BigDecimal calculatePersonalIncome(BigDecimal amount, ChannelType type, String userId) {
    User user = userService.findById(userId);
    switch (user.getType()) {
      case ADMIN:
        return calculateAdminIncome(amount, type);
      case PLATFORM:
        return calculatePlatformIncome(amount, type);
      case AGENT:
        Pair<UserFee, UserFee> userFeePair = userFeeService.findByUserIdAndChannelType(user.getId(), type);
        BigDecimal feeRate = userFeePair.getFirst().getValue();
        BigDecimal fee = userFeePair.getSecond().getValue();
        BigDecimal income = amount.multiply(feeRate).add(fee).setScale(2, RoundingMode.UP);
        return income;
      default:
        return null;
    }
  }

  private BigDecimal calculatePlatformIncome(BigDecimal amount, ChannelType type) {
    Channel channel = this.channelService.findByChannelType(type);
    BigDecimal incomeRate = channel.getFeeRate().subtract(channel.getPlatformFeeRate());
    BigDecimal incomeFee = channel.getFee().subtract(channel.getPlatformFee());
    BigDecimal income = amount.multiply(incomeRate).add(incomeFee);
    return income.setScale(2, RoundingMode.UP);
  }

  /**
   * 计算该笔交易中自身收益与下属收益
   *
   * @author chengshuo 2018年01月19日 18:18:38
   */
//  public Pair<BigDecimal, BigDecimal> calculatePersonalIncomeAndSub(BigDecimal amount, ChannelType type,
//      String userId, String agentId) {
//    List<SysUser> parents = sysUserService.listParents(agentId);
//    BigDecimal subIncome = new BigDecimal("0.00");
//    boolean anyMatch = parents.stream().anyMatch((sysUser) -> {
//      return Objects.equals(userId, sysUser.getId());
//    });
//    if (!anyMatch) {
//      return null;
//    }
//    parents.remove(0);
//    BigDecimal income = this.calculatePlatformIncome(amount, type);
//
//    for (SysUser sysUser : parents) {
//      Fee fee = feeService.findByUserId(sysUser.getId(), FeeType.SubAgentSplitRatio);
//      BigDecimal ratio = new BigDecimal(fee.getFeeRate().toString());
//      if (userId.equals(sysUser.getParentId())) {
//        subIncome = income.multiply(ratio).multiply(new BigDecimal("0.01")).setScale(2, RoundingMode.UP);
//        break;
//      }
//      income = income.multiply(ratio).multiply(new BigDecimal("0.01"));
//    }
//    income = income.setScale(2, RoundingMode.UP);
//    return Pair.of(income, subIncome);
//
//  }


}
