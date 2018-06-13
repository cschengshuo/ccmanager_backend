package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.dto.request.LoginDto;
import com.winsyo.ccmanager.dto.response.JwtDto;
import com.winsyo.ccmanager.service.AuthenticationService;
import com.winsyo.ccmanager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录Controller
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

  private AuthenticationService authenticationService;
  private UserService userService;

  public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
    this.authenticationService = authenticationService;
    this.userService = userService;
  }

  /**
   * 登录  获取JWT Token
   */
  @PostMapping(value = "login")
  public ResponseEntity login(@RequestBody LoginDto dto) throws AuthenticationException {
    String jwt = authenticationService.login(dto.getUsername(), dto.getPassword());
    User user = userService.findByUsername(dto.getUsername());
    return ok(new JwtDto(user, jwt));
  }

}
