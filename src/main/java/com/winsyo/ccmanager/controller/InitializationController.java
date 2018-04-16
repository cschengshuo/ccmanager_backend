package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.*;

import com.winsyo.ccmanager.service.InitializationService;
import com.winsyo.ccmanager.service.UserFeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/init")
public class InitializationController {

private InitializationService initializationService;

  public InitializationController(InitializationService initializationService) {
    this.initializationService = initializationService;
  }

  @PostMapping(value = "initUserFee")
  public ResponseEntity initUserFee(){
    initializationService.initUserFee();
    return ok("成功");
  }

  @PostMapping(value = "initChannel")
  public ResponseEntity initChannel(){
    initializationService.initChannel();
    return ok("成功");
  }


  @PostMapping(value = "initRole")
  public ResponseEntity initRole() {
    initializationService.initRole();
    return ok("成功");
  }

  @PostMapping(value = "initUserRole")
  public ResponseEntity initUserRole() {
    initializationService.initUserRole();
    return ok("成功");
  }

  @PostMapping(value = "initUserPassword")
  public ResponseEntity initUserPassword(){
    initializationService.initUserPassword();
    return ok("成功");
  }
}
