package com.winsyo.ccmanager.dto.view;

import com.winsyo.ccmanager.domain.AppUser;
import com.winsyo.ccmanager.dto.query.AppUserQueryDto;
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

  public AppUserDto(AppUserQueryDto dto) {
    AppUser appUser = dto.getAppUser();
    this.userId = appUser.getUserId();
    this.contactPhone = appUser.getContactPhone();
    this.inviteCode = appUser.getInviteCode();
    this.name = appUser.getName();
    this.IDNumber = appUser.getIDNumber();
    this.inviteCoded = appUser.getInviteCoded();
    this.recommendUserId = appUser.getRecommendUserId();
    this.agentId = appUser.getAgentId();
    this.agentName = dto.getAgentName();
    this.registerTime = appUser.getRegisterTime();
    this.approveTime = appUser.getApproveTime();
    this.upgradeTime = appUser.getUpgradeTime();
    this.seniorUser = appUser.isSeniorUser();
    this.canbalance = appUser.getCanbalance();
  }

}
