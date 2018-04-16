package com.winsyo.ccmanager.dto;

import com.winsyo.ccmanager.domain.Channel;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDto {

  private BigDecimal feeRate;

  private BigDecimal fee;

  private BigDecimal seniorFeeRate;

  private BigDecimal seniorFee;

  private String id;

  private String description;

  private String name;

  public ChannelDto(Channel channel) {
    this.feeRate = channel.getFeeRate();
    this.fee = channel.getFee();
    this.seniorFeeRate = channel.getSeniorFeeRate();
    this.seniorFee = channel.getSeniorFee();
    this.id = channel.getId();
    this.description = channel.getDescription();
    this.name = channel.getName();
  }
}
