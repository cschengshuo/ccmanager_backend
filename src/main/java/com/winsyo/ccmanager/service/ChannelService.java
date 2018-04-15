package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.Channel;
import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.domain.UserFee;
import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import com.winsyo.ccmanager.dto.ChannelFeeRateDto;
import com.winsyo.ccmanager.exception.EntityNotFoundException;
import com.winsyo.ccmanager.repository.ChannelRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {

  private ChannelRepository channelRepository;
  private UserService userService;
  private UserFeeService userFeeService;

  public ChannelService(ChannelRepository channelRepository, UserService userService, UserFeeService userFeeService) {
    this.channelRepository = channelRepository;
    this.userService = userService;
    this.userFeeService = userFeeService;
  }

  public List<Channel> findAll() {
    return channelRepository.findAll();
  }

  public Channel findByChannelType(ChannelType type) {
    return channelRepository.findByChannelType(type).orElseThrow(() -> new EntityNotFoundException(""));
  }

  public List<ChannelFeeRateDto> getSubFeeRateRange(String parentId) {
    List<ChannelFeeRateDto> results = new ArrayList<>();

    if (StringUtils.isEmpty(parentId)) {
      parentId = userService.getCurrentUserInfo().getId();
    }

    final String userId = parentId;

    List<Channel> channels = findAll();
    channels.forEach(channel -> {
      Pair<UserFee, UserFee> pair = userFeeService.findByUserIdAndChannelType(userId, channel.getChannelType());
      ChannelFeeRateDto feeRate = new ChannelFeeRateDto(channel.getChannelType().name() + "FeeRate", channel.getName() + "费率", pair.getFirst().getValue(), true);
      ChannelFeeRateDto fee = new ChannelFeeRateDto(channel.getChannelType().name() + "Fee", channel.getName() + "代收费", pair.getSecond().getValue(), true);
      results.add(feeRate);
      results.add(fee);
    });

    return results;
  }

  @Transactional
  public void initChannel(){
    channelRepository.deleteAll();

    // 计划通道
    Channel plan = new Channel();
    plan.setChannelType(ChannelType.PLAN);
    plan.setName("计划");
    plan.setFeeRate(new BigDecimal("0.008"));
    plan.setFee(new BigDecimal("0"));
    plan.setSeniorFeeRate(new BigDecimal("0.007"));
    plan.setSeniorFee(new BigDecimal("0"));
    plan.setPlatformFeeRate(new BigDecimal("0.0045"));
    plan.setPlatformFee(new BigDecimal("0"));
    plan.setCostFeeRate(new BigDecimal("0.0042"));
    plan.setCostFee(new BigDecimal("0"));
    plan.setDescription("计划通道");

    // C
    Channel c = new Channel();
    plan.setChannelType(ChannelType.C);
    plan.setName("通道C");
    plan.setFeeRate(new BigDecimal("0.005"));
    plan.setFee(new BigDecimal("2"));
    plan.setSeniorFeeRate(new BigDecimal("0.005"));
    plan.setSeniorFee(new BigDecimal("2"));
    plan.setPlatformFeeRate(new BigDecimal("0.0045"));
    plan.setPlatformFee(new BigDecimal("1"));
    plan.setCostFeeRate(new BigDecimal("0.004"));
    plan.setCostFee(new BigDecimal("1"));
    plan.setDescription("通道C");

    // D
    Channel d = new Channel();
    plan.setChannelType(ChannelType.D);
    plan.setName("通道D");
    plan.setFeeRate(new BigDecimal("0"));
    plan.setFee(new BigDecimal("55"));
    plan.setSeniorFeeRate(new BigDecimal("0"));
    plan.setSeniorFee(new BigDecimal("50"));
    plan.setPlatformFeeRate(new BigDecimal("0"));
    plan.setPlatformFee(new BigDecimal("41"));
    plan.setCostFeeRate(new BigDecimal("0"));
    plan.setCostFee(new BigDecimal("38"));
    plan.setDescription("通道D");

    // E
    Channel e = new Channel();
    plan.setChannelType(ChannelType.E);
    plan.setName("通道E");
    plan.setFeeRate(new BigDecimal("0.005"));
    plan.setFee(new BigDecimal("2"));
    plan.setSeniorFeeRate(new BigDecimal("0.005"));
    plan.setSeniorFee(new BigDecimal("2"));
    plan.setPlatformFeeRate(new BigDecimal("0.0045"));
    plan.setPlatformFee(new BigDecimal("1"));
    plan.setCostFeeRate(new BigDecimal("0.004"));
    plan.setCostFee(new BigDecimal("1"));
    plan.setDescription("通道E");

    // F
    Channel f = new Channel();
    plan.setChannelType(ChannelType.F);
    plan.setName("通道F");
    plan.setFeeRate(new BigDecimal("0.005"));
    plan.setFee(new BigDecimal("2"));
    plan.setSeniorFeeRate(new BigDecimal("0.005"));
    plan.setSeniorFee(new BigDecimal("2"));
    plan.setPlatformFeeRate(new BigDecimal("0.0045"));
    plan.setPlatformFee(new BigDecimal("1"));
    plan.setCostFeeRate(new BigDecimal("0.004"));
    plan.setCostFee(new BigDecimal("1"));
    plan.setDescription("通道F");
  }

}
