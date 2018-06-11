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
import org.springframework.web.bind.annotation.RequestParam;
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

  @PostMapping(value = "receiveResponse")
  public ResponseEntity receiveResponse(@RequestParam(required = false) String transCode, @RequestParam(required = false) String merchantId,
      @RequestParam(required = false) String respCode, @RequestParam(required = false) String sysTraceNum, @RequestParam(required = false) String merOrderNum,
      @RequestParam(required = false) String orderId, @RequestParam(required = false) String bussId, @RequestParam(required = false) String tranAmt,
      @RequestParam(required = false) String orderAmt, @RequestParam(required = false) String bankFeeAmt, @RequestParam(required = false) String integralAmt,
      @RequestParam(required = false) String vaAmt, @RequestParam(required = false) String bankAmt, @RequestParam(required = false) String bankId,
      @RequestParam(required = false) String integralSeq, @RequestParam(required = false) String vaSeq, @RequestParam(required = false) String bankSeq,
      @RequestParam(required = false) String tranDateTime, @RequestParam(required = false) String payMentTime, @RequestParam(required = false) String settleDate,
      @RequestParam(required = false) String currencyType, @RequestParam(required = false) String orderInfo, @RequestParam(required = false) String userId,
      @RequestParam(required = false) String userIp, @RequestParam(required = false) String reserver1, @RequestParam(required = false) String reserver2,
      @RequestParam(required = false) String reserver3, @RequestParam(required = false) String reserver4, @RequestParam(required = false) String signValue) {
    logger.info("接收回调");

    logger.info(transCode);
    logger.info(merchantId);
    logger.info(respCode);
    logger.info(sysTraceNum);
    logger.info(merOrderNum);
    logger.info(orderId);
    logger.info(bussId);
    logger.info(tranAmt);
    logger.info(orderAmt);
    logger.info(bankFeeAmt);
    logger.info(integralAmt);
    logger.info(vaAmt);
    logger.info(bankAmt);
    logger.info(bankId);
    logger.info(integralSeq);
    logger.info(vaSeq);
    logger.info(bankSeq);
    logger.info(tranDateTime);
    logger.info(payMentTime);
    logger.info(settleDate);
    logger.info(currencyType);
    logger.info(orderInfo);
    logger.info(userId);
    logger.info(userIp);
    logger.info(reserver1);
    logger.info(reserver2);
    logger.info(reserver3);
    logger.info(reserver4);
    logger.info(signValue);
    return ok("success");
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
