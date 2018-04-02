package com.winsyo.ccmanager.dto;

import com.winsyo.ccmanager.domain.User;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto implements Serializable {

  private User user;

  private String jwt;

}
