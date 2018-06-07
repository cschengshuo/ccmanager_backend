package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.winsyo.ccmanager.domain.enumerate.ReportType;
import com.winsyo.ccmanager.dto.ReportDto;
import com.winsyo.ccmanager.service.AppUserReportService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 手机用户统计报表Controller
 */
@RestController
@RequestMapping(value = "/appuser_report")
public class AppUserReportController {

  private AppUserReportService appUserReportService;

  public AppUserReportController(AppUserReportService appUserReportService) {
    this.appUserReportService = appUserReportService;
  }

  /**
   * 获取收益统计报表
   * @param type
   * @param start
   * @param end
   * @return
   */
  @GetMapping(value = "getReport")
  public ResponseEntity getReport(int type, @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start,
      @DateTimeFormat(iso = ISO.DATE_TIME) @RequestParam(required = false) LocalDateTime end) {
    ReportType reportType = ReportType.indexOf(type);

    List<ReportDto> reports = appUserReportService.getReport(reportType, start, end);
    return ok(reports);
  }
}
