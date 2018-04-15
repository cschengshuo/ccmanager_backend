package com.winsyo.ccmanager.domain;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "appuser")
public class AppUser {

  @Id
  @Column
  private String userId;

  @Column
  private String contactPhone;

  @Column
  private String password;

  @Column
  private String inviteCode;

  @Column
  private String name;

  @Column
  private String IDNumber;

  @Column(name = "invitecoded")
  private String inviteCoded;

  @Column
  private String recommendUserId;

  @Column
  private String agentId;

  @Column
  private Instant registerTime;

  @Column
  private Instant approveTime;

  @Column
  private Instant upgradeTime;

  @Column
  private boolean isSeniorUser;

}
