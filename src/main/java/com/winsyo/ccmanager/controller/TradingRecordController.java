package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.winsyo.ccmanager.domain.TradingRecord;
import com.winsyo.ccmanager.domain.enumerate.PayWayTag;
import com.winsyo.ccmanager.dto.response.TradingRecordDto;
import com.winsyo.ccmanager.service.TradingRecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 交易记录Controller
 */
@RestController
@RequestMapping(value = "/trading_record")
public class TradingRecordController {

  private TradingRecordService tradingRecordService;

  public TradingRecordController(TradingRecordService tradingRecordService) {
    this.tradingRecordService = tradingRecordService;
  }

  /**
   * 查询用户交易记录
   */
  @GetMapping(value = "findAll")
  public ResponseEntity findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, String cardNo, String userName) {
    PageRequest pagination = PageRequest.of(page, size);
    Page<TradingRecord> all = tradingRecordService.findAll(cardNo, userName, null, pagination);
    Page<TradingRecordDto> result = all.map(TradingRecordDto::new);
    return ok(result);
  }

  /**
   * 查询用户提现记录
   */
  @GetMapping(value = "findWithDrawRecords")
  public ResponseEntity findWithDrawRecords(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
    PageRequest pagination = PageRequest.of(page, size);
    Page<TradingRecord> all = tradingRecordService.findAll(null, null, PayWayTag.WITHDRAW, pagination);
    Page<TradingRecordDto> result = all.map(TradingRecordDto::new);
    return ok(result);
  }

}
