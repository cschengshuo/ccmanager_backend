package com.winsyo.ccmanager.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "payment_channel")
public class Channel {

  @Id
  private String id;

  @Column
  private String name;

  /**
   * 通道类型
   */
  @Column(name = "channel_type")
  private ChannelType channelType;

  /**
   * 通道费率
   */
  @Column(name = "fee_rate")
  private BigDecimal feeRate;

  /**
   * 通道代收费
   */
  @Column
  private BigDecimal fee;

  /**
   * 高级用户通道费率
   */
  @Column(name = "senior_fee_rate")
  private BigDecimal seniorFeeRate;

  /**
   * 高级用户通道代收费
   */
  @Column(name = "senior_fee")
  private BigDecimal seniorFee;

  /**
   * 成本费率
   */
  @Column(name = "platform_fee_rate")
  private BigDecimal platformFeeRate;

  /**
   * 成本代收费
   */
  @Column(name = "platform_fee")
  private BigDecimal platformFee;

  /**
   * 成本费率
   */
  @Column(name = "cost_fee_rate")
  private BigDecimal costFeeRate;

  /**
   * 成本代收费
   */
  @Column(name = "cost_fee")
  private BigDecimal costFee;

  /**
   * 通道描述
   */
  @Column
  private String description;

  @Column
  private Boolean enable;

}
