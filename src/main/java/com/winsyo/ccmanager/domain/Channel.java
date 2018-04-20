package com.winsyo.ccmanager.domain;

import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@NoArgsConstructor
@Entity
@Table(name = "payment_channel")
public class Channel {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
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
  @Column(name = "fee_rate", precision = 6, scale = 5)
  private BigDecimal feeRate;

  /**
   * 通道代收费
   */
  @Column(precision = 6, scale = 2)
  private BigDecimal fee;

  /**
   * 高级用户通道费率
   */
  @Column(name = "senior_fee_rate", precision = 6, scale = 5)
  private BigDecimal seniorFeeRate;

  /**
   * 高级用户通道代收费
   */
  @Column(name = "senior_fee", precision = 6, scale = 2)
  private BigDecimal seniorFee;

  /**
   * 成本费率
   */
  @Column(name = "platform_fee_rate", precision = 6, scale = 5)
  private BigDecimal platformFeeRate;

  /**
   * 成本代收费
   */
  @Column(name = "platform_fee", precision = 6, scale = 2)
  private BigDecimal platformFee;

  /**
   * 成本费率
   */
  @Column(name = "cost_fee_rate", precision = 6, scale = 5)
  private BigDecimal costFeeRate;

  /**
   * 成本代收费
   */
  @Column(name = "cost_fee", precision = 6, scale = 2)
  private BigDecimal costFee;

  /**
   * 通道描述
   */
  @Column(length = 1000)
  private String description;

  @Column
  private Boolean enable;

}
