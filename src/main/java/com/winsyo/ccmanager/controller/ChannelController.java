package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.winsyo.ccmanager.domain.Channel;
import com.winsyo.ccmanager.dto.ChannelDto;
import com.winsyo.ccmanager.dto.ChannelFeeRateDto;
import com.winsyo.ccmanager.service.ChannelService;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    List<ChannelDto> dtos = all.stream().map(ChannelDto::new).collect(Collectors.toList());
    return ok(dtos);
  }

  @GetMapping(value = "getSubFeeRateRange")
  public ResponseEntity getSubFeeRateRange(String parentId) {
    List<ChannelFeeRateDto> dtos = channelService.getSubFeeRateRange(parentId);
    return ok(dtos);
  }

}
