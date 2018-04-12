package com.winsyo.ccmanager.util;

import com.winsyo.ccmanager.config.JwtUser;
import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.exception.UserNotFoundException;
import com.winsyo.ccmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class SecurityUtils {

  public static JwtUser getCurrentUser(){
    JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return jwtUser;
  }

}
