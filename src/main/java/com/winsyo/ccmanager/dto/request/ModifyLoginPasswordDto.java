package com.winsyo.ccmanager.dto.request;

import lombok.Data;

@Data
public class ModifyLoginPasswordDto {

  private String oldPassword;
  private String password;
}
