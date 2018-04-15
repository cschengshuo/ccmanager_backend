package com.winsyo.ccmanager.util;

import com.winsyo.ccmanager.config.JwtUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

  public static JwtUser getCurrentUser() {
    JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return jwtUser;
  }

}
