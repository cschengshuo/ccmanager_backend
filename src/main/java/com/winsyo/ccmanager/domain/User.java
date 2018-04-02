package com.winsyo.ccmanager.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "sys_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String id;

  /**
   * 姓名
   */
  @Column
  private String name;

  /**
   * 登录名
   */
  @Column
  private String loginName;

  /**
   * 密码
   */
  @Column
  private String password;

  /**
   * 手机号
   */
  @Column
  private String mobile;

  /**
   * 邀请码
   */
  @Column
  private String inviteCode;

  /**
   * 身份证号
   */
  @Column
  private String identityCard;

  /**
   * 代理层级 TODO 改名
   */
  @Column
  private Integer userType;

  /**
   * 上级用户ID
   */
  @Column
  private String parentId;

  /**
   * 顶级用户ID
   */
  @Column
  private String topUserId;

  /**
   * 证书
   */
  @Column
  private String licence;

  /**
   * 紧急联系人
   */
  @Column
  private String emergencyContact;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private List<Role> roles;

}
