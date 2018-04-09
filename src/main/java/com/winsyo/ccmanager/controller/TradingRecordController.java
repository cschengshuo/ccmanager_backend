package com.winsyo.ccmanager.controller;

import com.winsyo.ccmanager.domain.TradingRecord;
import com.winsyo.ccmanager.service.TradingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(value = "/trading_record")
public class TradingRecordController {

  private TradingRecordService tradingRecordService;

  @Autowired
  public TradingRecordController(TradingRecordService tradingRecordService) {
    this.tradingRecordService = tradingRecordService;
  }

  @GetMapping(value = "findAll")
  public ResponseEntity findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
    PageRequest pagination = PageRequest.of(page, size);
    Page<TradingRecord> all = tradingRecordService.findAll(pagination);
    return ok(all);
  }

}
