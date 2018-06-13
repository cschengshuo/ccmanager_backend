package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.TradingRecord;
import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.domain.UserFee;
import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import com.winsyo.ccmanager.domain.enumerate.ReportType;
import com.winsyo.ccmanager.dto.ReportDto;
import com.winsyo.ccmanager.util.Utils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class IncomeReportService {

  private AppUserService appUserService;
  private TradingRecordService tradingRecordService;
  private IncomeService incomeService;
  private UserService userService;
  private UserFeeService userFeeService;

  public IncomeReportService(AppUserService appUserService, TradingRecordService tradingRecordService, IncomeService incomeService, UserService userService,
      UserFeeService userFeeService) {
    this.appUserService = appUserService;
    this.tradingRecordService = tradingRecordService;
    this.incomeService = incomeService;
    this.userService = userService;
    this.userFeeService = userFeeService;
  }

  /**
   * 获取收益报表
   */
  public List<ReportDto> getReport(ReportType type, LocalDateTime startDate, LocalDateTime endDate) {
    LocalDateTime start = null;
    LocalDateTime end = null;
    switch (type) {
      case TODAY:
        start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        break;
      case MONTH:
        start = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth());
        end = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth());
        break;
      case TIME:
        start = startDate;
        end = endDate;
    }

    User user = Utils.getCurrentUser();

    List<ReportDto> dtos = new ArrayList<>();
    switch (user.getType()) {
      case ADMIN:
        List<TradingRecord> allRecords = tradingRecordService.findTradingRecordsByTime(start, end);
        List<ReportDto> adminReports = dealAdminRecord(allRecords);
        dtos.addAll(adminReports);
        break;
      case PLATFORM:
      case AGENT:
        List<TradingRecord> selfRecords = tradingRecordService.findTradingRecordsByTime(user.getId(), start, end);
        List<ReportDto> selfReports = dealSelfRecord(selfRecords, user.getId());
        dtos.addAll(selfReports);
        List<ReportDto> childrenReports = getChildrenReport(user.getId(), start, end);
        dtos.addAll(childrenReports);
        break;
    }

    ReportDto total = new ReportDto("汇总");
    ReportDto plan = new ReportDto("计划");
    ReportDto channelC = new ReportDto("通道C");
    ReportDto channelD = new ReportDto("通道D");
    ReportDto channelE = new ReportDto("通道E");
    ReportDto channelF = new ReportDto("通道F");

    add(dtos, plan, channelC, channelD, channelE, channelF);
    addReport(total, plan);
    addReport(total, channelC);
    addReport(total, channelD);
    addReport(total, channelE);
    addReport(total, channelF);

    return Arrays.asList(total, plan, channelC, channelD, channelE, channelF);
  }

  private void add(List<ReportDto> childrenReports, ReportDto plan, ReportDto channelC, ReportDto channelD, ReportDto channelE, ReportDto channelF) {
    childrenReports.forEach((report) -> {
      ChannelType channelType = report.getChannelType();
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

        case E:
          addReport(channelE, report);
          break;

        case F:
          addReport(channelF, report);
          break;
      }
    });
  }

  private List<ReportDto> dealAdminRecord(List<TradingRecord> records) {
    List<ReportDto> reports = new ArrayList<>();

    records.forEach((record) -> {
      ReportDto dto = new ReportDto();
      BigDecimal money = record.getMoney();
      ChannelType type = ChannelType.indexOf(record.getPayWayTAG());
      if (type == null) {
        return;
      }
      dto.setChannelType(type);

      BigDecimal income = incomeService.calculateAdminIncome(money, type);

      dto.setSubAmount(money);
      dto.setIncome(income);
      dto.setIncomeFromSub(income);

      reports.add(dto);
    });
    return reports;

  }

  /**
   * 根据交易记录获取该用户所得收益报表
   *
   * @author chengshuo 2018年01月26日 17:30:45
   */
  private List<ReportDto> dealSelfRecord(List<TradingRecord> records, String userId) {
    List<ReportDto> reports = new ArrayList<>();

    records.forEach((record) -> {
      ReportDto dto = new ReportDto();
      BigDecimal money = record.getMoney();
      ChannelType type = ChannelType.indexOf(record.getPayWayTAG());
      if (type == null) {
        return;
      }
      dto.setChannelType(type);

      Pair<UserFee, UserFee> userFeePair = userFeeService.findByUserIdAndChannelType(userId, type);

      BigDecimal feeRate = userFeePair.getFirst().getValue();
      BigDecimal fee = userFeePair.getSecond().getValue();
      BigDecimal income = money.multiply(feeRate).add(fee).setScale(2, RoundingMode.UP);

      dto.setSelfAmount(money);
      dto.setSelfIncome(income);
      dto.setIncome(income);

      reports.add(dto);
    });
    return reports;
  }

  private List<ReportDto> getChildrenReport(String userId, LocalDateTime start, LocalDateTime end) {
    List<String> children = userService.getAllChildren(userId).stream().map(User::getId).collect(Collectors.toList());
    List<TradingRecord> subRecords = tradingRecordService.findTradingRecordsByTime(children, start, end);
    return dealChildrenDto(subRecords, userId);
  }

  private List<ReportDto> dealChildrenDto(List<TradingRecord> records, String userId) {
    List<ReportDto> reports = new ArrayList<>();

    records.forEach((record) -> {
      ReportDto dto = new ReportDto();
      BigDecimal money = record.getMoney();
      ChannelType type = ChannelType.indexOf(record.getPayWayTAG());
      if (type == null) {
        return;
      }
      dto.setChannelType(type);

      Pair<UserFee, UserFee> userFeePair = userFeeService.findByUserIdAndChannelType(userId, type);

      BigDecimal feeRate = userFeePair.getFirst().getValue();
      BigDecimal fee = userFeePair.getSecond().getValue();
      BigDecimal income = money.multiply(feeRate).add(fee).setScale(2, RoundingMode.UP);

      dto.setSubAmount(money);
      dto.setIncome(income);
      dto.setIncomeFromSub(income);

      reports.add(dto);
    });
    return reports;
  }

  private void addReport(ReportDto lhs, ReportDto rhs) {
    lhs.setIncome(lhs.getIncome().add(rhs.getIncome()));
    lhs.setSelfAmount(lhs.getSelfAmount().add(rhs.getSelfAmount()));
    lhs.setSelfIncome(lhs.getSelfIncome().add(rhs.getSelfIncome()));
    lhs.setSubAmount(lhs.getSubAmount().add(rhs.getSubAmount()));
    lhs.setIncomeFromSub(lhs.getIncomeFromSub().add(rhs.getIncomeFromSub()));
    lhs.setSubIncome(lhs.getSubIncome().add(rhs.getSubIncome()));
  }
}
