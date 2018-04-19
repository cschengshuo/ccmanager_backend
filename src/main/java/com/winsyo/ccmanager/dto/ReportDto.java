package com.winsyo.ccmanager.dto;

import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ReportDto {

  /**
   * 收益（包含需要分成给下属的部分）
   */
  private BigDecimal income;

  /**
   * 名下用户交易量
   */
  private BigDecimal selfAmount;

  /**
   * 名下用户交易代理的收益
   */
  private BigDecimal selfIncome;

  /**
   * 下级代理交易总额
   */
  private BigDecimal subAmount;

  /**
   * 下级代理带来的收益
   */
  private BigDecimal incomeFromSub;

  /**
   * 需分成给下级代理的金额
   */
  private BigDecimal subIncome;

  /**
   * 通道类型
   */
  private ChannelType channelType;

  /**
   * 通道名称
   */
  private String channelName;

  public ReportDto() {
    this.income = new BigDecimal("0.00");
    this.selfAmount = new BigDecimal("0.00");
    this.selfIncome = new BigDecimal("0.00");
    this.subAmount = new BigDecimal("0.00");
    this.incomeFromSub = new BigDecimal("0.00");
    this.subIncome = new BigDecimal("0.00");
  }

  public ReportDto(String channelName) {
    this();
    this.channelName = channelName;
  }

}
