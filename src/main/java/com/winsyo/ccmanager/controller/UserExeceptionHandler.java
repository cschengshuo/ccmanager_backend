package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.status;

import com.winsyo.ccmanager.exception.UserNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class UserExeceptionHandler {

  @ExceptionHandler(value = {UserNotFoundException.class})
  public ResponseEntity signupFailed(Exception ex, WebRequest req) {
    Map<String, String> errorMsg = new HashMap<>();
    errorMsg.put("code", "conflict");
    errorMsg.put("message", ex.getMessage());
    return status(HttpStatus.CONFLICT).body(errorMsg);
  }

}
