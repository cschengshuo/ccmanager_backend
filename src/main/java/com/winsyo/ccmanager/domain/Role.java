package com.winsyo.ccmanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

/**
 * 代理角色
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  /**
   * 角色值
   */
  @Column(unique = true)
  private String role;

  /**
   * 角色名称
   */
  @Column
  private String name;

}
