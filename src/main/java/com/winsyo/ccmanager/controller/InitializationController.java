package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.winsyo.ccmanager.service.AppUserService;
import com.winsyo.ccmanager.service.InitializationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 平台数据初始化Controller
 */
@RestController
@RequestMapping(value = "/init")
public class InitializationController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private InitializationService initializationService;
  private AppUserService appUserService;

  public InitializationController(InitializationService initializationService, AppUserService appUserService) {
    this.initializationService = initializationService;
    this.appUserService = appUserService;
  }

  /**
   * 初始化用户费率
   */
  @PreAuthorize(value = "hasAuthority('ADMIN')")
  @PostMapping(value = "initUserFee")
  public ResponseEntity initUserFee() {
    initializationService.initUserFee();
    return ok("成功");
  }

  /**
   * 初始化通道信息
   */
  @PreAuthorize(value = "hasAuthority('ADMIN')")
  @PostMapping(value = "initChannel")
  public ResponseEntity initChannel() {
    initializationService.initChannel();
    return ok("成功");
  }

  /**
   * 初始化角色信息
   */
  @PreAuthorize(value = "hasAuthority('ADMIN')")
  @PostMapping(value = "initRole")
  public ResponseEntity initRole() {
    initializationService.initRole();
    return ok("成功");
  }

  /**
   * 初始化用户角色
   */
  @PreAuthorize(value = "hasAuthority('ADMIN')")
  @PostMapping(value = "initUserRole")
  public ResponseEntity initUserRole() {
    initializationService.initUserRole();
    return ok("成功");
  }

  /**
   * 初始化所有用户密码
   */
  @PreAuthorize(value = "hasAuthority('ADMIN')")
  @PostMapping(value = "initUserPassword")
  public ResponseEntity initUserPassword() {
    initializationService.initUserPassword();
    return ok("成功");
  }

  /**
   * 初始化平台用户费率
   */
  @PreAuthorize(value = "hasAuthority('ADMIN')")
  @PostMapping(value = "initPlatformUserFee")
  public ResponseEntity initPlatformUserFee() {
    initializationService.initPlatformUserFee();
    return ok("成功");
  }

  /**
   * 进行合利宝商户注册
   */
  @PreAuthorize(value = "hasAuthority('ADMIN')")
  @PostMapping(value = "doCreateMerchant")
  public ResponseEntity doCreateMerchant() {
    appUserService.doCreateMerchant();
    return ok("成功");
  }

}
