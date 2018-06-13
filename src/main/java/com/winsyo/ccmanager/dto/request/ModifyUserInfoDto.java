package com.winsyo.ccmanager.dto.request;

import lombok.Data;

@Data
public class ModifyUserInfoDto {

  private String id;
  private String name;
  private String phone;
  private String identityCard;

}
