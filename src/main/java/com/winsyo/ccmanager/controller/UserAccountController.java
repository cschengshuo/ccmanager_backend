package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.winsyo.ccmanager.domain.Channel;
import com.winsyo.ccmanager.domain.UserAccount;
import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import com.winsyo.ccmanager.service.UserAccountService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user_account")
public class UserAccountController {

  private UserAccountService userAccountService;

  public UserAccountController(UserAccountService userAccountService) {
    this.userAccountService = userAccountService;
  }

  @GetMapping(value = "findByUserId")
  public ResponseEntity findByUserId(String userId) {
    List<UserAccount> userAccount = userAccountService.findByUserId(userId);
    return ok(userAccount);
  }


}
