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

/**
 * 平台公告、消息
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "messageinfo")
@DynamicUpdate
public class MessageInfo {

  @Id
  @GeneratedValue
  private Long id;

  /**
   * 消息内容
   */
  @Column
  private String text;

  /**
   * 创建时间
   */
  @Column
  private LocalDateTime createtime;

  /**
   * 消息类型
   */
  @Column
  private String type;

}
