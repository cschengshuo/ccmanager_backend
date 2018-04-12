package com.winsyo.ccmanager.exception;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(String userName) {
    super("未找到实体");
  }

}
