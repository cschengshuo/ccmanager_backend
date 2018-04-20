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

@Entity
@Table(name = "system_config")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemConfig {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "config_type")
  private SystemConfigType configType;

  @Column
  private String description;

  @Column
  private String value;

}
