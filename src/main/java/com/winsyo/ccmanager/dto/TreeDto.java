package com.winsyo.ccmanager.dto;

import com.winsyo.ccmanager.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeDto {

  private String title;

  private String value;

  public TreeDto(User user) {
    this.value = user.getId();
    this.title = user.getName();
  }

}
