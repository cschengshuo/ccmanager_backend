package com.winsyo.ccmanager.dto;

import com.winsyo.ccmanager.domain.Channel;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ChannelDto extends PlatformChannelDto {

  private BigDecimal costFeeRate;

  private BigDecimal costFee;

  public ChannelDto(Channel channel) {
    super(channel);
    this.costFeeRate = channel.getCostFeeRate();
    this.costFee = channel.getCostFee();
  }

}
