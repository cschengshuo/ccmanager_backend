package com.winsyo.ccmanager.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.winsyo.ccmanager.domain.Channel;
import com.winsyo.ccmanager.domain.TradingRecord;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradingRecordDto {

  private String recordId;

  @JSONField(format="yyyy-MM-dd HH:mm:ss")
  private Instant time;

  private BigDecimal money;

  private int status;

  private int payWayTAG;

  private String type;

  private String cardNo;

  private String bankCode;

  private String inCardNo;

  private String recordNo;

  private String planId;

  private String userName;

  public TradingRecordDto(TradingRecord tradingRecord,String userName) {
    this.recordId = tradingRecord.getRecordId();
    this.time = tradingRecord.getTime();
    this.money = tradingRecord.getMoney();
    this.status = tradingRecord.getStatus();
    if (tradingRecord.getPayWayTAG() != null){
      this.payWayTAG = tradingRecord.getPayWayTAG().index();
    }
    this.type = tradingRecord.getType();
    this.cardNo = tradingRecord.getCardNo();
    this.bankCode = tradingRecord.getBankCode();
    this.inCardNo = tradingRecord.getInCardNo();
    this.recordNo = tradingRecord.getRecordNo();
    this.planId = tradingRecord.getPlanId();
    this.userName = userName;
  }
}
