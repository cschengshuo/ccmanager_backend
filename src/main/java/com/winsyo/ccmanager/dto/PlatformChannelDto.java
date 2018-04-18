package com.winsyo.ccmanager.dto;

import com.winsyo.ccmanager.domain.Channel;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class PlatformChannelDto {

  private BigDecimal feeRate;

  private BigDecimal fee;

  private BigDecimal seniorFeeRate;

  private BigDecimal seniorFee;

  private BigDecimal platformFeeRate;

  private BigDecimal platformFee;

  private String id;

  private String description;

  private String name;

  public PlatformChannelDto(Channel channel) {
    this.feeRate = channel.getFeeRate();
    this.fee = channel.getFee();
    this.seniorFeeRate = channel.getSeniorFeeRate();
    this.seniorFee = channel.getSeniorFee();
    this.platformFeeRate = channel.getPlatformFeeRate();
    this.platformFee = channel.getPlatformFee();
    this.id = channel.getId();
    this.description = channel.getDescription();
    this.name = channel.getName();
  }

}
