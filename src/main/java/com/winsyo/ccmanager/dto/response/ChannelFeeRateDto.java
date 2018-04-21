package com.winsyo.ccmanager.dto.response;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChannelFeeRateDto {

  private String label;

  private BigDecimal max;

  private BigDecimal min = new BigDecimal("0");

  private BigDecimal value;

  private String index;

  private BigDecimal step;

  private boolean feeRate;

}
