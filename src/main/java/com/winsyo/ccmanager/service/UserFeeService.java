package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.UserFee;
import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import com.winsyo.ccmanager.dto.FeeRateDto;
import com.winsyo.ccmanager.exception.EntityNotFoundException;
import com.winsyo.ccmanager.repository.UserFeeRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class UserFeeService {

  private UserFeeRepository userFeeRepository;

  public UserFeeService(UserFeeRepository userFeeRepository) {
    this.userFeeRepository = userFeeRepository;
  }

  public UserFee findByUserIdAndChannelTypeAndFeeRate(String userId, ChannelType type, boolean isFeeRate) {
    UserFee userFee = userFeeRepository.findByUserIdAndChannelTypeAndFeeRate(userId, type, isFeeRate).orElseThrow(() -> new EntityNotFoundException("未找到用户费率", userId));
    return userFee;
  }

  public Pair<UserFee, UserFee> findByUserIdAndChannelType(String userId, ChannelType type) {
    UserFee feeRate = findByUserIdAndChannelTypeAndFeeRate(userId, type, true);
    UserFee fee = findByUserIdAndChannelTypeAndFeeRate(userId, type, false);

    return Pair.of(feeRate, fee);
  }

  @Transactional
  public Pair<UserFee, UserFee> createUserFee(String userId, ChannelType type) {
    List<ChannelType> values = Arrays.asList(ChannelType.values());
    values.forEach(channelType -> {
      UserFee feeRate = new UserFee();
      feeRate.setChannelType(channelType);
      feeRate.setUserId(userId);
      feeRate.setValue(new BigDecimal("0.0005"));
      feeRate.setFeeRate(true);
      userFeeRepository.save(feeRate);

      UserFee fee = new UserFee();
      fee.setChannelType(channelType);
      fee.setUserId(userId);
      fee.setValue(new BigDecimal("0"));
      fee.setFeeRate(false);
      userFeeRepository.save(fee);
    });

    return findByUserIdAndChannelType(userId, type);

  }

  @Transactional
  public void createUserFee(Map<String, FeeRateDto> map, String userId) {
    map.forEach((s, feeRateDto) -> {
      String[] split = StringUtils.split(s, '_');
      ChannelType channelType = ChannelType.valueOf(split[0]);
      UserFee fee = new UserFee();
      fee.setValue(feeRateDto.getValue());
      fee.setUserId(userId);
      fee.setChannelType(channelType);

      if (StringUtils.equals(split[1], "FeeRate")) {
        fee.setFeeRate(true);
      } else {
        fee.setFeeRate(false);
      }
      userFeeRepository.save(fee);
    });
  }

  @Transactional
  public void removeUserFee(String userId) {
    userFeeRepository.deleteByUserId(userId);
  }

}
