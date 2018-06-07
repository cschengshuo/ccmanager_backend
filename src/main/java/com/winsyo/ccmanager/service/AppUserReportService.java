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
public class AppUserReportService {

  private AppUserService appUserService;
  private TradingRecordService tradingRecordService;
  private IncomeService incomeService;
  private UserService userService;
  private UserFeeService userFeeService;

  public AppUserReportService(AppUserService appUserService, TradingRecordService tradingRecordService, IncomeService incomeService, UserService userService,
      UserFeeService userFeeService) {
    this.appUserService = appUserService;
    this.tradingRecordService = tradingRecordService;
    this.incomeService = incomeService;
    this.userService = userService;
    this.userFeeService = userFeeService;
  }

  /**
   * 获取收益报表
   * @param type
   * @param startDate
   * @param endDate
   * @return
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

    return null;

  }


}
