package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.ChannelType;
import com.winsyo.ccmanager.domain.UserFee;
import com.winsyo.ccmanager.exception.EntityNotFoundException;
import com.winsyo.ccmanager.repository.UserFeeRepository;
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
}
