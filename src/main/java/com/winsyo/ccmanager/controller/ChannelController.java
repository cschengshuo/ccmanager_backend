package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.winsyo.ccmanager.domain.Channel;
import com.winsyo.ccmanager.dto.request.ModifyChannelDto;
import com.winsyo.ccmanager.dto.response.ChannelDto;
import com.winsyo.ccmanager.dto.response.ChannelFeeRateDto;
import com.winsyo.ccmanager.dto.response.PlatformChannelDto;
import com.winsyo.ccmanager.service.ChannelService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通道管理Controller
 */
@RestController
@RequestMapping(value = "/channel")
public class ChannelController {

  private ChannelService channelService;

  public ChannelController(ChannelService channelService) {
    this.channelService = channelService;
  }

  /**
   * 系统管理员查询所有通道信息
   */
  @PreAuthorize(value = "hasAuthority('ADMIN')")
  @GetMapping(value = "findAllForAdmin")
  public ResponseEntity findAllForAdmin() {
    List<Channel> all = channelService.findAll();
    List<ChannelDto> dtos = all.stream().map(ChannelDto::new).collect(Collectors.toList());
    return ok(dtos);
  }

  /**
   * 平台管理员查询所有通道信息
   */
  @PreAuthorize(value = "hasAuthority('PLATFORM')")
  @GetMapping(value = "findAllForPlatform")
  public ResponseEntity findAllForPlatform() {
    List<Channel> all = channelService.findAll();
    List<PlatformChannelDto> dtos = all.stream().map(PlatformChannelDto::new).collect(Collectors.toList());
    return ok(dtos);
  }

  /**
   * 系统管理员查询通道信息
   */
  @PreAuthorize(value = "hasAuthority('ADMIN')")
  @GetMapping(value = "findByIdForAdmin")
  public ResponseEntity findByIdForAdmin(String id) {
    Channel channel = channelService.findById(id);
    return ok(new ChannelDto(channel));
  }

  /**
   * 平台管理员查询通道信息
   */
  @PreAuthorize(value = "hasAuthority('PLATFORM')")
  @GetMapping(value = "findByIdForPlatform")
  public ResponseEntity findByIdForPlatform(String id) {
    Channel channel = channelService.findById(id);
    return ok(new PlatformChannelDto(channel));
  }

  /**
   * 修改通道
   */
  @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'PLATFORM')")
  @PostMapping(value = "modify")
  public ResponseEntity modify(@RequestBody ModifyChannelDto dto) {
    channelService.modify(dto);
    return ok(true);
  }

  /**
   * 查询下属代理所能设置的通道费率范围
   */
  @GetMapping(value = "getSubFeeRateRange")
  public ResponseEntity getSubFeeRateRange(String parentId) {
    List<ChannelFeeRateDto> dtos = channelService.getSubFeeRateRange(parentId);
    return ok(dtos);
  }

}
