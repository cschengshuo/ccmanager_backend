package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.winsyo.ccmanager.config.JwtUser;
import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.exception.UserNotFoundException;
import com.winsyo.ccmanager.service.RoleService;
import com.winsyo.ccmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

  private UserService userService;
  private RoleService roleService;

  @Autowired
  public UserController(UserService userService, RoleService roleService) {
    this.userService = userService;
    this.roleService = roleService;
  }


  @GetMapping(value = "getCurrentUserInfo")
  public ResponseEntity getCurrentUserInfo() throws AuthenticationException {
    JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = userService.findByLoginName(jwtUser.getUsername()).orElseThrow(() -> {
      return new UserNotFoundException("未找到该用户");
    });
    return ok(user);
  }

  @PostMapping(value = "initRole")
  public ResponseEntity initRole() {
    roleService.initRole();
    return ok("成功");
  }

  @PostMapping(value = "initUserRole")
  public ResponseEntity initUserRole() {
    userService.initUserRole();
    return ok("成功");
  }


}
