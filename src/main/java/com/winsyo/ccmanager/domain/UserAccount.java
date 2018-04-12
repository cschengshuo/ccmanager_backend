package com.winsyo.ccmanager.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_account")
public class UserAccount {

  @Id
  private String id;

  @Column(name = "user_id")
  private String userId;

  @Column(precision = 13, scale = 2)
  private BigDecimal balance;

  @Column(name = "pre_settlement", precision = 13, scale = 2)
  private BigDecimal preSettlement;

}
