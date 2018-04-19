package com.winsyo.ccmanager.dto;

import com.winsyo.ccmanager.domain.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppUserQueryDto {

  private AppUser appUser;

  private String agentName;

}
