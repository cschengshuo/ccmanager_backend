package com.winsyo.ccmanager.domain;

import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import com.winsyo.ccmanager.domain.enumerate.SettlementStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 * 提现记录
 */
@Data
@Entity
@Table(name = "settlement_record")
public class SettlementRecord {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  /**
   * 提现金额
   */
  @Column(precision = 13, scale = 2)
  private BigDecimal amount;

  /**
   * 提现人ID
   */
  @Column(name = "user_id")
  private String userId;

  /**
   * 提现日期
   */
  @Column(name = "settle_date")
  private LocalDateTime settleDate;

  /**
   * 通道类型
   */
  @Column(name = "channel_type")
  private ChannelType channelType;

  /**
   * 提现记录状态
   */
  @Column
  private SettlementStatus status;

}
