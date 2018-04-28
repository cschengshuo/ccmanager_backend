package com.winsyo.ccmanager.dto.response;

import lombok.Data;

@Data
public class WithdrawDto {

  private String agentName;

  private ChannelWithdrawDto preSettlement;

  private ChannelWithdrawDto withdraw;

}
