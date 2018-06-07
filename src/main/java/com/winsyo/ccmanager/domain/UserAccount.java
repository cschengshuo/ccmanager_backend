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

/**
 * 代理账户
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "user_account")
public class UserAccount {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  /**
   * 代理ID
   */
  @Column(name = "user_id")
  private String userId;

  /**
   * 已提现金额
   */
  @Column(precision = 13, scale = 2)
  private BigDecimal balance;

  /**
   * 待提现金额
   */
  @Column(name = "pre_settlement", precision = 13, scale = 2)
  private BigDecimal preSettlement;

  /**
   * 账户通道类型
   */
  @Column
  private ChannelType type;

}
