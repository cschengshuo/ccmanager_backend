package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.AppUser;
import com.winsyo.ccmanager.domain.TradingRecord;
import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import com.winsyo.ccmanager.dto.ReportDto;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

  private AppUserService appUserService;
  private TradingRecordService tradingRecordService;
  private IncomeService incomeService;


  public List<ReportDto> getPersonReport(String userId, int type, boolean isAdmin, Instant startTime, Instant endTime) {
    if (startTime == null || endTime == null) {
      return null;
    }
    ReportDto total = new ReportDto("汇总");
    ReportDto plan = new ReportDto("计划");
    ReportDto channelC = new ReportDto("通道C");
    ReportDto channelD = new ReportDto("通道D");
    List<TradingRecord> records = tradingRecordService.listMonthTradingRecords();
    List<ReportDto> reports = getReportFromRecord(records, userId, isAdmin);

    reports.forEach((report) -> {
      ChannelType channelType = ChannelType.indexOf(report.getChannelType());
      switch (channelType) {
        case PLAN:
          addReport(plan, report);
          break;

        case C:
          addReport(channelC, report);
          break;

        case D:
          addReport(channelD, report);
          break;

        default:
          break;
      }
    });

    addReport(total, plan);
    addReport(total, channelC);
    addReport(total, channelD);

    List<ReportDto> result = Arrays.asList(total, plan, channelC, channelD);
    return result;
  }

  /**
   * 获取统计数据
   * @author chengshuo 2018年01月16日 15:31:44
   * @param userId 用户ID
   * @param type
   * @param isAdmin
   * @return
   */
  public List<ReportDto> getTodayReport(String userId, int type, boolean isAdmin) {
    ReportDto total = new ReportDto("汇总");
    ReportDto plan = new ReportDto("计划");
    ReportDto channelC = new ReportDto("通道C");
    ReportDto channelD = new ReportDto("通道D");


    List<TradingRecord> records = new ArrayList<>();

    switch (type) {
      case 1:
        records = tradingRecordService.listTodayTradingRecords();
        break;
      case 2: // TODO 改进
        records = tradingRecordService.listMonthTradingRecords();
      default:
        break;
    }

    List<ReportDto> reports = getReportFromRecord(records, userId, isAdmin);

    reports.forEach((report) -> {
      ChannelType channelType = ChannelType.indexOf(report.getChannelType());
      switch (channelType) {
        case PLAN:
          addReport(plan, report);
          break;

        case C:
          addReport(channelC, report);
          break;

        case D:
          addReport(channelD, report);
          break;

        default:
          break;
      }
    });

    addReport(total, plan);
    addReport(total, channelC);
    addReport(total, channelD);

    List<ReportDto> result = Arrays.asList(total, plan, channelC, channelD);
    return result;
  }

  /**
   * 根据交易记录获取该用户所得收益报表
   * @author chengshuo 2018年01月26日 17:30:45
   * @param records
   * @param userId
   * @param isAdmin
   * @return
   */
  private List<ReportDto> getReportFromRecord(List<TradingRecord> records, String userId, boolean isAdmin) {
    List<ReportDto> reports = new ArrayList<>();
    records.forEach((record) -> {
      ReportDto dto = new ReportDto();
      BigDecimal money = new BigDecimal(Double.toString(record.getMoney()));
      // 获取该笔交易归属的手机用户
      String appUserId = record.getUserId();
      AppUser appUser = appUserService.findById(appUserId);
      if (appUser == null) {
        return;
      }
      boolean isSenior = appUser.isSeniorUser();
      // 获取该笔交易的交易类型
      ChannelType type = record.getPayWayTAG();
      if (type == null) {
        return;
      }
      dto.setChannelType(type.index());

      if (isAdmin) { // 当前用户为管理员
        dto.setAmount(money);
        BigDecimal income = incomeService.calculateAdminIncome(money, type);
        dto.setIncome(income);
      } else if (StringUtils.equals(userId, appUser.getAgentId())) { // 手机用户归属于当前用户
        dto.setAmount(money);

        BigDecimal income = incomeService.calculatePersonalIncome(money, type, userId);
        dto.setIncome(income);
        dto.setIncomeFromUser(income);
      } else {
//        Pair<BigDecimal, BigDecimal> income = incomeService.calculatePersonalIncomeAndSub(money, type,
//            userId, appUser.getAgentId());
//        if (income == null) { // 当前用户不在该笔交易的获益队列中
//          return;
//        }

//        dto.setSubAmount(money);
//
//        dto.setIncome(income.getFirst());
//        dto.setIncomeFromSub(income.getFirst());
//        dto.setSubIncome(income.getSecond());
      }
      reports.add(dto);
    });
    return reports;
  }

  private void addReport(ReportDto lhs, ReportDto rhs) {
    lhs.setAmount(lhs.getAmount().add(rhs.getAmount()));
    lhs.setIncome(lhs.getIncome().add(rhs.getIncome()));
    lhs.setIncomeFromSub(lhs.getIncomeFromSub().add(rhs.getIncomeFromSub()));
    lhs.setIncomeFromUser(lhs.getIncomeFromUser().add(rhs.getIncomeFromUser()));
    lhs.setSubAmount(lhs.getSubAmount().add(rhs.getSubAmount()));
    lhs.setSubIncome(lhs.getSubIncome().add(rhs.getSubIncome()));
  }
}
