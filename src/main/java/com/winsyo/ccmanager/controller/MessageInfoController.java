package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.winsyo.ccmanager.domain.MessageInfo;
import com.winsyo.ccmanager.service.MessageInfoService;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 平台公告管理
 */
@RestController
@RequestMapping(value = "/message")
public class MessageInfoController {

  private MessageInfoService messageInfoService;

  public MessageInfoController(MessageInfoService messageInfoService) {
    this.messageInfoService = messageInfoService;
  }

  /**
   * 发送公告
   */
  @PostMapping(value = "sendAnnouncement")
  public ResponseEntity sendAnnouncement(@RequestBody Map<String, String> map) {
    String text = map.get("text");
    messageInfoService.sendAnnouncement(text);
    return ok(true);
  }

  /**
   * 查询所有公告
   */
  @GetMapping(value = "listAnnouncement")
  public ResponseEntity listAnnouncement(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    PageRequest pagination = PageRequest.of(page, size);
    Page<MessageInfo> messageInfos = messageInfoService.listMessageInfo(pagination);
    return ok(messageInfos);
  }

}
