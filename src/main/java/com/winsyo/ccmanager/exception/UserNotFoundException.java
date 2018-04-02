package com.winsyo.ccmanager.exception;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String userName) {
    super("用户名或密码错误");
  }

}
