package com.winsyo.ccmanager.domain;

import com.winsyo.ccmanager.domain.enumerate.SystemConfigType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统配置
 */
@Entity
@Table(name = "system_config")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemConfig {

  @Id
  @GeneratedValue
  private Long id;

  /**
   * 系统配置类型
   */
  @Column(name = "config_type")
  private SystemConfigType configType;

  /**
   * 配置名称
   */
  @Column
  private String description;

  /**
   * 内容
   */
  @Column(length = 1000)
  private String value;

}
