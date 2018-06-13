package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.domain.enumerate.UserType;
import com.winsyo.ccmanager.dto.request.CreateUserDto;
import com.winsyo.ccmanager.dto.request.ModifyLoginPasswordDto;
import com.winsyo.ccmanager.dto.request.ModifyUserDto;
import com.winsyo.ccmanager.dto.request.ModifyUserInfoDto;
import com.winsyo.ccmanager.dto.response.TreeDto;
import com.winsyo.ccmanager.service.RoleService;
import com.winsyo.ccmanager.service.UserService;
import com.winsyo.ccmanager.util.Utils;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 代理Controller
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

  private UserService userService;
  private RoleService roleService;

  public UserController(UserService userService, RoleService roleService) {
    this.userService = userService;
    this.roleService = roleService;
  }

  /**
   * 获取当前用户信息
   */
  @GetMapping(value = "getCurrentUserInfo")
  public ResponseEntity getCurrentUserInfo() throws AuthenticationException {
    User user = Utils.getCurrentUser();
    return ok(user);
  }

  /**
   * 根据ID查询用户信息
   */
  @GetMapping(value = "getUserById")
  public ResponseEntity getUserById(String id) {
    User user = userService.findById(id);
    return ok(user);
  }

  /**
   * 根据上级ID查询下级代理列表
   */
  @GetMapping(value = "findUsersByParentId")
  public ResponseEntity findUsersByParentId(String id) {
    List<User> users = userService.findUsersByParentId(id);
    List<TreeDto> dtos = users.stream().map(userService::map).collect(Collectors.toList());
    return ok(dtos);
  }

  /**
   * 获取代理树 根节点
   */
  @GetMapping(value = "getUserTreeRoot")
  public ResponseEntity getUserTreeRoot() {
    TreeDto dto = userService.getUserTreeRoot();
    return ok(dto);
  }

  /**
   * 检查登录名是否重复
   */
  @GetMapping(value = "checkLoginNameExist")
  public ResponseEntity checkLoginNameExist(String username) {
    boolean exist = userService.checkUsernameExist(username);
    return ok(exist);
  }

  /**
   * 创建用户
   */
  @PostMapping(value = "createUser")
  public ResponseEntity createUser(@RequestBody CreateUserDto dto) {
    userService.createAgentUser(dto);
    return ok(true);
  }

  /**
   * 修改用户
   */
  @PostMapping(value = "modifyUser")
  public ResponseEntity modifyUser(@RequestBody ModifyUserDto dto) {
    userService.modifyUser(dto);
    return ok(true);
  }

  /**
   * 查询所有用户
   *
   * @deprecated 查询所有用户的接口没有地方需要使用
   */
  @Deprecated
  @GetMapping(value = "findAll")
  public ResponseEntity findAll() {
    List<User> all = userService.findAll();
    return ok(all);
  }

  /**
   * 查询用户列表
   *
   * @param loginName 查询条件 登录名
   */
  @GetMapping(value = "findUsers")
  public ResponseEntity findUsers(String loginName) {
    User user = Utils.getCurrentUser();
    if (user.getType() == UserType.ADMIN) {
      user = userService.getPlatformAdministrator();
    }
    List<User> all = userService.findUsers(user.getId(), loginName);
    return ok(all);
  }

  /**
   * 个人中心 修改用户信息
   */
  @PostMapping(value = "modifyUserInfo")
  public ResponseEntity modifyUserInfo(@RequestBody ModifyUserInfoDto dto) {
    userService.modifyUserInfo(dto);
    return ok(true);
  }

  /**
   * 个人中心 修改登录密码
   */
  @PostMapping(value = "modifyLoginPassword")
  public ResponseEntity modifyLoginPassword(@RequestBody ModifyLoginPasswordDto dto) {
    User user = Utils.getCurrentUser();
    userService.setPassword(user.getUsername(), dto.getOldPassword(), dto.getPassword());
    return ok(true);
  }

}
