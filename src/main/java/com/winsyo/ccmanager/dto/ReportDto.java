package com.winsyo.ccmanager.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ReportDto {

  private BigDecimal amount;

  private BigDecimal subAmount;

  private BigDecimal income;

  private BigDecimal incomeFromUser;

  private BigDecimal incomeFromSub;

  private BigDecimal subIncome;

  private BigDecimal cost;

  private Integer channelType;

  private String channelName;

  public ReportDto() {
    amount = new BigDecimal("0.00");
    subAmount = new BigDecimal("0.00");
    income = new BigDecimal("0.00");
    incomeFromUser = new BigDecimal("0.00");
    incomeFromSub = new BigDecimal("0.00");
    subIncome = new BigDecimal("0.00");
    cost = new BigDecimal("0.00");

  }

  public ReportDto(String channelName) {
    this();
    this.channelName = channelName;
  }

}
