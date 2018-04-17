package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.dto.CreateUserDto;
import com.winsyo.ccmanager.dto.ModifyUserDto;
import com.winsyo.ccmanager.dto.TreeDto;
import com.winsyo.ccmanager.dto.TreeNodeDto;
import com.winsyo.ccmanager.service.RoleService;
import com.winsyo.ccmanager.service.UserService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    User user = userService.getCurrentUserInfo();
    return ok(user);
  }

  @GetMapping(value = "getUserById")
  public ResponseEntity getUserById(String id) {
    User user = userService.findById(id);
    return ok(user);
  }

  @GetMapping(value = "findUsersByParentId")
  public ResponseEntity findUsersByParentId(String id) {
    List<User> users = userService.findUsersByParentId(id);
    List<TreeDto> dtos = users.stream().map(userService::map).collect(Collectors.toList());
    return ok(dtos);
  }

  @GetMapping(value = "getUserTreeRoot")
  public ResponseEntity getUserTreeRoot() {
    User user = userService.getPlatformAdministrator();
    TreeNodeDto dto = new TreeNodeDto(user);
    List<User> users = userService.findUsersByParentId(user.getId());
    List<TreeDto> dtos = users.stream().map(userService::map).collect(Collectors.toList());
    dto.setChildren(dtos);
    dto.setExpand(true);
    return ok(dto);
  }

  @GetMapping(value = "checkLoginNameExist")
  public ResponseEntity checkLoginNameExist(String username) {
    boolean exist = userService.checkLoginNameExist(username);
    return ok(exist);
  }

  @PostMapping(value = "createUser")
  public ResponseEntity createUser(@RequestBody CreateUserDto dto) {
    userService.createUser(dto);
    return ok(true);
  }

  @PostMapping(value = "modifyUser")
  public ResponseEntity modifyUser(@RequestBody ModifyUserDto dto){
    userService.modifyUser(dto);
    return ok(true);
  }

  @GetMapping(value = "findAll")
  public ResponseEntity findAll() {
    List<User> all = userService.findAll();
    return ok(all);
  }

  @GetMapping(value = "findUsers")
  public ResponseEntity findUsers(String loginName) {
    List<User> all = userService.findUsers(loginName);
    return ok(all);
  }

}
