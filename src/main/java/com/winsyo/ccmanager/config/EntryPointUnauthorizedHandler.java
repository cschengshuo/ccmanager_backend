package com.winsyo.ccmanager.config;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 自定401返回值
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
    try {
      if (e instanceof BadCredentialsException) {
        response.sendError(401, "登录失败，用户名或密码错误");
      } else {
        response.sendError(401, e.getMessage());
      }
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

}