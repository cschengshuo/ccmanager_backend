package com.winsyo.ccmanager.dto.query;

import com.winsyo.ccmanager.domain.TradingRecord;
import lombok.Data;

@Data
public class TradingRecordQueryDto {

  private TradingRecord tradingRecord;

  private String username;

  public TradingRecordQueryDto(TradingRecord tradingRecord, String username) {
    this.tradingRecord = tradingRecord;
    this.username = username;
  }
}
