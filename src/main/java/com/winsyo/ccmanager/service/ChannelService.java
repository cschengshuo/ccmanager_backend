package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.Channel;
import com.winsyo.ccmanager.repository.ChannelRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {

  private ChannelRepository channelRepository;

  @Autowired
  public ChannelService(ChannelRepository channelRepository) {
    this.channelRepository = channelRepository;
  }

  public List<Channel> findAll(){
    return channelRepository.findAll();
  }
}
