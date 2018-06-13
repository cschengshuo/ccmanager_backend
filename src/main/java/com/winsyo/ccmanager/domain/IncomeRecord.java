package com.winsyo.ccmanager.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@NoArgsConstructor
@Entity
@Table(name = "income_record")
public class IncomeRecord {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  private BigDecimal income;

  @ManyToOne(fetch = FetchType.EAGER)
  private TradingRecord tradingRecord;

  @ManyToOne(fetch = FetchType.EAGER)
  private User user;

}
