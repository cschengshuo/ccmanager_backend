package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.winsyo.ccmanager.dto.RechargeRecordDto;
import com.winsyo.ccmanager.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/util")
public class UtilController {

  private UtilService utilService;

  public UtilController(UtilService utilService) {
    this.utilService = utilService;
  }

  @PostMapping(value = "addRechargeRecord")
  public ResponseEntity addRechargeRecord(@RequestBody RechargeRecordDto dto) {
    utilService.addRechargeRecord(dto);
    return ok(true);
  }

  @GetMapping(value = "sendAnnouncement")
  public ResponseEntity sendAnnouncement(String text) {
    utilService.sendAnnouncement(text);
    return ok(true);
  }

}
