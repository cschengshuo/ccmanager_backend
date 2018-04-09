package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.winsyo.ccmanager.domain.Channel;
import com.winsyo.ccmanager.service.ChannelService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/channel")
public class ChannelController {

  private ChannelService channelService;

  @Autowired
  public ChannelController(ChannelService channelService) {
    this.channelService = channelService;
  }

  @GetMapping(value = "findAll")
  public ResponseEntity findAll() {
    List<Channel> all = channelService.findAll();
    return ok(all);
  }
}
