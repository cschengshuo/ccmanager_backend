package com.winsyo.ccmanager.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "messageinfo")
@DynamicUpdate
public class MessageInfo {

  @Id
  @GeneratedValue
  private Long id;

  @Column
  private String text;

  @Column
  private LocalDateTime createtime;

  @Column
  private String type;

}
