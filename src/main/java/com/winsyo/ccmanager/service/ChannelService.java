package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.Channel;
import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.domain.UserFee;
import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import com.winsyo.ccmanager.domain.enumerate.UserType;
import com.winsyo.ccmanager.dto.ChannelFeeRateDto;
import com.winsyo.ccmanager.dto.ModifyChannelDto;
import com.winsyo.ccmanager.exception.EntityNotFoundException;
import com.winsyo.ccmanager.repository.ChannelRepository;
import com.winsyo.ccmanager.util.Utils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
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
    return channelRepository.findAll(Sort.by("channelType"));
  }

  public Channel findByChannelType(ChannelType type) {
    return channelRepository.findByChannelType(type).orElseThrow(() -> new EntityNotFoundException("未找到该通道", type.name()));
  }

  public Channel findById(String id) {
    return channelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("未找到该通道", id));
  }

  public List<ChannelFeeRateDto> getSubFeeRateRange(String parentId) {
    List<ChannelFeeRateDto> results = new ArrayList<>();

    if (StringUtils.isEmpty(parentId)) {
      User currentUser = Utils.getCurrentUser();
      if (currentUser.getType() != UserType.ADMIN) {
        parentId = currentUser.getId();
      } else {
        parentId = userService.getPlatformAdministrator().getId();
      }
    }

    final String userId = parentId;

    List<Channel> channels = findAll();
    channels.forEach(channel -> {
      Pair<UserFee, UserFee> pair = userFeeService.findByUserIdAndChannelType(userId, channel.getChannelType());

      ChannelFeeRateDto feeRate = new ChannelFeeRateDto();
      feeRate.setIndex(channel.getChannelType().name() + "_FeeRate");
      feeRate.setLabel(channel.getName() + "费率");
      feeRate.setMax(pair.getFirst().getValue());
      feeRate.setStep(new BigDecimal("0.0001"));
      feeRate.setValue(new BigDecimal("0"));
      feeRate.setFeeRate(true);

      ChannelFeeRateDto fee = new ChannelFeeRateDto();
      fee.setIndex(channel.getChannelType().name() + "_Fee");
      fee.setLabel(channel.getName() + "代收费");
      fee.setMax(pair.getSecond().getValue());
      fee.setStep(new BigDecimal("0.5"));
      fee.setValue(new BigDecimal("0"));
      fee.setFeeRate(false);

      results.add(feeRate);
      results.add(fee);
    });

    return results;
  }

  @Transactional
  public void modify(ModifyChannelDto dto) {
    Channel channel = findById(dto.getId());

    if (dto.getPlatformFeeRate() != null && dto.getPlatformFeeRate().compareTo(channel.getPlatformFeeRate()) != 0) {
      channel.setPlatformFeeRate(dto.getPlatformFeeRate());
    }
    if (dto.getPlatformFee() != null && dto.getPlatformFee().compareTo(channel.getPlatformFee()) != 0) {
      channel.setPlatformFee(dto.getPlatformFee());
    }
    if (dto.getCostFeeRate() != null && dto.getCostFeeRate().compareTo(channel.getCostFeeRate()) != 0) {
      channel.setCostFeeRate(dto.getCostFeeRate());
    }
    if (dto.getCostFee() != null && dto.getCostFee().compareTo(channel.getCostFee()) != 0) {
      channel.setCostFee(dto.getCostFee());
    }
    if (!StringUtils.equals(dto.getDescription(), channel.getDescription())) {
      channel.setDescription(dto.getDescription());
    }

    channelRepository.save(channel);
  }

}
