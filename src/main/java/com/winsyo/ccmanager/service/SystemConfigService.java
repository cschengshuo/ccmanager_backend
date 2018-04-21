package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.SystemConfig;
import com.winsyo.ccmanager.domain.enumerate.SystemConfigType;
import com.winsyo.ccmanager.exception.EntityNotFoundException;
import com.winsyo.ccmanager.repository.SystemConfigRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SystemConfigService {

  private SystemConfigRepository systemConfigRepository;

  public SystemConfigService(SystemConfigRepository systemConfigRepository) {
    this.systemConfigRepository = systemConfigRepository;
  }

  public SystemConfig findByConfigType(SystemConfigType type) {
    return systemConfigRepository.findByConfigType(type).orElseThrow(() -> new EntityNotFoundException("未找到系统设置", type.name()));
  }

  @Transactional
  public SystemConfig save(SystemConfig config) {
    return systemConfigRepository.save(config);
  }
}
