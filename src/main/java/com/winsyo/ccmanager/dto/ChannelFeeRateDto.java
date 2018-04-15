package com.winsyo.ccmanager.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChannelFeeRateDto {

  private String label;

  private boolean status;

  private BigDecimal max;

  private BigDecimal min = new BigDecimal("0");

  private BigDecimal value;

  private String index;

  private BigDecimal step = new BigDecimal("0.0001");

  public ChannelFeeRateDto(String index, String label, BigDecimal max, boolean status) {
    this.label = label;
    this.index = index;
    this.max = max;
    this.status = status;

  }
}
