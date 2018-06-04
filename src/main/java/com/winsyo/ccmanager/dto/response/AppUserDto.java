package com.winsyo.ccmanager.dto.response;

import com.winsyo.ccmanager.domain.AppUser;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AppUserDto {

  private String userId;

  private String contactPhone;

  private String inviteCode;

  private String name;

  private String IDNumber;

  private String inviteCoded;

  private String recommendUserId;

  private String agentId;

  private String agentName;

  private LocalDateTime registerTime;

  private LocalDateTime approveTime;

  private LocalDateTime upgradeTime;

  private boolean seniorUser;

  private BigDecimal canbalance;

  public AppUserDto(AppUser appUser) {
    this.userId = appUser.getUserId();
    this.contactPhone = appUser.getContactPhone();
    this.inviteCode = appUser.getInviteCode();
    this.name = appUser.getName();
    this.IDNumber = appUser.getIdNumber();
    this.inviteCoded = appUser.getInviteCoded();
    this.recommendUserId = appUser.getRecommendUserId();
    this.agentId = appUser.getAgent().getId();
    this.agentName = appUser.getAgent().getName();
    this.registerTime = appUser.getRegisterTime();
    this.approveTime = appUser.getApproveTime();
    this.upgradeTime = appUser.getUpgradeTime();
    this.seniorUser = appUser.isSeniorUser();
    this.canbalance = appUser.getCanbalance();
  }

}
