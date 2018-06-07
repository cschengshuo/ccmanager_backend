package com.winsyo.ccmanager.domain;

import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 * 用户费率
 */
@Data
@Entity
@Table(name = "user_fee")
public class UserFee {

  /**
   * ID
   */
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  /**
   * 费率值
   */
  @Column(precision = 19, scale = 5)
  private BigDecimal value;

  /**
   * 通道类型
   */
  @Column
  private ChannelType channelType;

  /**
   * 用户ID
   */
  @Column
  private String userId;

  /**
   * true为费率 false为代收费
   */
  @Column
  private boolean feeRate;

}
