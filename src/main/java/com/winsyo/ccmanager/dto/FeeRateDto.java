package com.winsyo.ccmanager.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeeRateDto {

  private BigDecimal value;

  private String label;

  private String index;
}
