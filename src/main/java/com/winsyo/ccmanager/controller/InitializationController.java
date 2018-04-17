package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.*;

import com.winsyo.ccmanager.service.InitializationService;
import com.winsyo.ccmanager.service.UserFeeService;
import javax.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

  @PreAuthorize(value ="hasAuthority('ADMIN')")
  @PostMapping(value = "initUserFee")
  public ResponseEntity initUserFee(){
    initializationService.initUserFee();
    return ok("成功");
  }

  @PreAuthorize(value ="hasAuthority('ADMIN')")
  @PostMapping(value = "initChannel")
  public ResponseEntity initChannel(){
    initializationService.initChannel();
    return ok("成功");
  }

  @PreAuthorize(value ="hasAuthority('ADMIN')")
  @PostMapping(value = "initRole")
  public ResponseEntity initRole() {
    initializationService.initRole();
    return ok("成功");
  }

  @PreAuthorize(value ="hasAuthority('ADMIN')")
  @PostMapping(value = "initUserRole")
  public ResponseEntity initUserRole() {
    initializationService.initUserRole();
    return ok("成功");
  }

  @PreAuthorize(value ="hasRole('ADMIN')")
  @PostMapping(value = "initUserPassword")
  public ResponseEntity initUserPassword(){
    initializationService.initUserPassword();
    return ok("成功");
  }
}
