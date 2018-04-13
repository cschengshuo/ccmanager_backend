package com.winsyo.ccmanager.domain;

import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import com.winsyo.ccmanager.domain.enumerate.OK;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "trading_record")
public class TradingRecord {

  @Id
  private String recordId;

  /**
   * 金额
   */
  @Column
  private Double money;

  /**
   * 时间
   */
  @Column
  private Instant time;

  /**
   * 类型
   */
  @Column
  private String type;

  /**
   * 卡号
   */
  @Column
  private String cardNo;

  /**
   * 开户行名称
   */
  @Column
  private String bankCode;

  /**
   * 人员ID
   */
  @Column
  private String userId;

  /**
   * 转入卡号
   */
  @Column
  private String inCardNo;

  /**
   * 交易编号
   */
  @Column
  private String recordNo;

  /**
   * 计划编号
   */
  @Column
  private String planId;

  /**
   * 0 计划转账记录 1 提现转账记录
   */
  @Column
  private int status;

  /**
   * 结算状态
   */
  @Column
  private boolean settlementStatus;

  /**
   * 是否成功
   */
  @Column(name = "isOK")
  private boolean ok;

  /**
   * 通道标识
   */
  @Column
  private ChannelType payWayTAG;

}
