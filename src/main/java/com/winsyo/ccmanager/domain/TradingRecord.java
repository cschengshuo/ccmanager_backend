package com.winsyo.ccmanager.domain;

import com.winsyo.ccmanager.domain.enumerate.PayWayTag;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 手机用户交易记录
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "trading_record")
@DynamicUpdate
public class TradingRecord {

  /**
   * 记录ID
   */
  @Id
  private String recordId;

  /**
   * 金额
   */
  @Column
  private BigDecimal money;

  /**
   * 时间
   */
  @Column
  private LocalDateTime time;

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
   * 交易手机用户
   */
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "userId")
  private AppUser appUser;

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
  private PayWayTag payWayTAG;

}
