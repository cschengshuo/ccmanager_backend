package com.winsyo.ccmanager.dto;

import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModifyUserDto {

  private String id;

  private String parentId;

  private String name;

  private String phone;

  private String idCard;

  private Map<String, FeeRateDto> feeRate;

  private String areaCode;

}
