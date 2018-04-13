package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import com.winsyo.ccmanager.domain.UserFee;
import com.winsyo.ccmanager.exception.EntityNotFoundException;
import com.winsyo.ccmanager.repository.UserFeeRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFeeService {

  private UserFeeRepository userFeeRepository;

  @Autowired
  public UserFeeService(UserFeeRepository userFeeRepository) {
    this.userFeeRepository = userFeeRepository;
  }

  public UserFee findByUserIdAndChannelType(String userId, ChannelType type)  {
    UserFee userFee = userFeeRepository.findByUserIdAndChannelType(userId, type).orElseThrow(() -> new EntityNotFoundException(""));
    return userFee;
  }

  @Transactional
  public UserFee createUserFee(String userId,ChannelType type) {
    List<ChannelType> values =Arrays.asList(ChannelType.values()) ;
    values.forEach(channelType -> {
      UserFee userFee = new UserFee();
      userFee.setChannelType(channelType);
      userFee.setUserId(userId);
      userFee.setValue(new BigDecimal("0.0005"));
      userFeeRepository.save(userFee);
    });

    return findByUserIdAndChannelType(userId, type);

  }
}
