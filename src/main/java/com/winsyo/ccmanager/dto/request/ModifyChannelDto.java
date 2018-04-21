package com.winsyo.ccmanager.dto.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyChannelDto {

  private String id;

  private BigDecimal platformFeeRate;

  private BigDecimal platformFee;

  private BigDecimal costFeeRate;

  private BigDecimal costFee;

  private String description;

}
