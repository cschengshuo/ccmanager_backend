package com.winsyo.ccmanager.config;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * 自定403返回值
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {
    try {
      response.sendError(403, "你没有执行该操作的权限");
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

}