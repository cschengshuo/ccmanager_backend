package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.dto.LoginDto;
import com.winsyo.ccmanager.exception.EntityNotFoundException;
import com.winsyo.ccmanager.service.AuthenticationService;
import com.winsyo.ccmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

  private AuthenticationService authenticationService;
  private UserService userService;

  @Autowired
  public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
    this.authenticationService = authenticationService;
    this.userService = userService;
  }

  @PostMapping(value = "login")
  public ResponseEntity login(String username, String password) throws AuthenticationException {
    String jwt = authenticationService.login(username, password);
    User user = userService.findByLoginName(username).orElseThrow(() -> new EntityNotFoundException(username));
    return ok(new LoginDto(user, jwt));
  }


}
