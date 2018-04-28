package com.winsyo.ccmanager.domain;

import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import com.winsyo.ccmanager.domain.enumerate.SettlementStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "settlement_record")
public class SettlementRecord {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(precision = 13, scale = 2)
  private BigDecimal amount;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "settle_date")
  private LocalDateTime settleDate;

  @Column(name = "channel_type")
  private ChannelType channelType;

  @Column
  private SettlementStatus status;

}
