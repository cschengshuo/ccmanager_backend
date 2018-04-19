package com.winsyo.ccmanager.exception;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(String message, String id) {
    super(message + "标识符：" + id);
  }

}
