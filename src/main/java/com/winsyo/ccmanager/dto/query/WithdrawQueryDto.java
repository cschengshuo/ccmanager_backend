package com.winsyo.ccmanager.dto.query;

import com.winsyo.ccmanager.domain.SettlementRecord;
import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.domain.UserAccount;
import lombok.Data;

@Data
public class WithdrawQueryDto {

  private UserAccount userAccount;

  private User user;

  private SettlementRecord settlementRecord;

}
