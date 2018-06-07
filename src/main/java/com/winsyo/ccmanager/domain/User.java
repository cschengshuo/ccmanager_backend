package com.winsyo.ccmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsyo.ccmanager.domain.enumerate.UserType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 代理平台用户
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "sys_user")
@DynamicUpdate
public class User implements UserDetails {

  /**
   * ID
   */
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  /**
   * 姓名
   */
  @Column
  private String name;

  /**
   * 登录名
   */
  @Column(name = "login_name")
  private String username;

  /**
   * 密码
   */
  @Column
  private String password;

  /**
   * 手机号
   */
  @Column
  private String phone;

  /**
   * 邀请码
   */
  @Column
  private String inviteCode;

  /**
   * 平台用户类型
   */
  @Column
  @Enumerated(EnumType.ORDINAL)
  private UserType type;

  /**
   * 身份证号
   */
  @Column(name = "identity_card")
  private String identityCard;

  /**
   * 代理层级 TODO 改名
   */
  @Column(name = "user_type")
  private Integer userType;

  /**
   * 上级用户ID
   */
  @Column(name = "parent_id")
  private String parentId;

  @Column(name = "parent_ids")
  private String parentIds;

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

  /**
   * 用户角色列表
   */
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private List<Role> roles;

  /**
   * 代理区域
   */
  @Column(name = "agent_area_code")
  private String agentAreaCode;

  /**
   * 账户是否启用
   */
  @Column(nullable = false)
  private boolean enabled;

  /**
   * 账户是否失效
   */
  @Column(nullable = false)
  private boolean expired;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<Role> roles = this.getRoles();
    List<SimpleGrantedAuthority> authorities = roles.stream().map((role) -> role.getRole()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    return authorities;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return !expired;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

}
