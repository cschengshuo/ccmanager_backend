package com.winsyo.ccmanager.dto.request;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {

  private String parentId;

  private String name;

  private String loginName;

  private String password;

  private String phone;

  private String idCard;

  private Map<String, FeeRateDto> feeRate;

  private String areaCode;

}
