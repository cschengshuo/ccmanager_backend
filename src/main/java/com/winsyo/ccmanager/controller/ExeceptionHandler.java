package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.status;

import com.winsyo.ccmanager.exception.EntityNotFoundException;
import com.winsyo.ccmanager.exception.OperationFailureException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExeceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());


  @ExceptionHandler(value = {EntityNotFoundException.class})
  public ResponseEntity entityNotFoundExceptionHandler(Exception ex, WebRequest req) {
    Map<String, String> errorMsg = new HashMap<>();
    errorMsg.put("code", "conflict");
    errorMsg.put("message", ex.getMessage());
    logger.error("未找到实体",ex);
    return status(HttpStatus.CONFLICT).body(errorMsg);
  }

  @ExceptionHandler(value = {OperationFailureException.class})
  public ResponseEntity operationFailureExceptionHandler(Exception ex, WebRequest req) {
    Map<String, String> errorMsg = new HashMap<>();
    errorMsg.put("code", "conflict");
    errorMsg.put("message", ex.getMessage());
    return status(HttpStatus.CONFLICT).body(errorMsg);
  }

}
