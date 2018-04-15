package com.winsyo.ccmanager.domain;

import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "user_fee")
public class UserFee {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(precision = 19, scale = 5)
  private BigDecimal value;

  @Column
  private ChannelType channelType;

  @Column
  private String userId;

  @Column
  private boolean feeRate;

}
