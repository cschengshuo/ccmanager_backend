package com.winsyo.ccmanager.domain;

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
 * 手机用户
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "appuser")
@DynamicUpdate
public class AppUser {

  /**
   * ID
   */
  @Id
  @Column
  private String userId;

  /**
   * 手机号
   */
  @Column
  private String contactPhone;

  /**
   * 密码
   */
  @Column
  private String password;

  /**
   * 邀请码
   */
  @Column
  private String inviteCode;

  /**
   * 姓名
   */
  @Column
  private String name;

  /**
   * 身份证号
   */
  @Column(name = "IDNumber")
  private String idNumber;

  /**
   * 被邀请码
   */
  @Column(name = "invitecoded")
  private String inviteCoded;

  /**
   * 邀请人ID
   */
  @Column
  private String recommendUserId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "agentId")
  private User agent;

  /**
   * 注册时间
   */
  @Column
  private LocalDateTime registerTime;

  /**
   * 认证时间
   */
  @Column
  private LocalDateTime approveTime;

  /**
   * 升级为高级用户时间
   */
  @Column
  private LocalDateTime upgradeTime;

  /**
   * 是否是高级用户
   */
  @Column
  private boolean isSeniorUser;

  /**
   * 用户账户余额
   */
  @Column
  private BigDecimal canbalance;

  @Column
  private String userNo;

}
