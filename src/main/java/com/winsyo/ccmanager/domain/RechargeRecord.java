package com.winsyo.ccmanager.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/*
 * 充值记录
 */
@Data
@Entity
@Table(name = "recharge_record")
public class RechargeRecord {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column
  private String userId;

  @Column(precision = 19, scale = 2)
  private BigDecimal money;

  @Column
  private LocalDateTime time;


}
