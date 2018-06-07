package com.winsyo.ccmanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 代理申请
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "appuser")
public class AgentApply {

  @Id
  private String id;

  /**
   * 代理姓名
   */
  @Column
  private String name;

  /**
   * 公司名称
   */
  @Column
  private String company;

  /**
   * 手机号
   */
  @Column
  private String phone;

}
