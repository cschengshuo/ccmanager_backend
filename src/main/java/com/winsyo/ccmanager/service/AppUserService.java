package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.AppUser;
import com.winsyo.ccmanager.dto.AppUserDto;
import com.winsyo.ccmanager.dto.AppUserQueryDto;
import com.winsyo.ccmanager.exception.EntityNotFoundException;
import com.winsyo.ccmanager.repository.AppUserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

  private AppUserRepository appUserRepository;
  private UserService userService;

  public AppUserService(AppUserRepository appUserRepository, UserService userService) {
    this.appUserRepository = appUserRepository;
    this.userService = userService;
  }

  public AppUser findById(String id) {
    return appUserRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("未找到该手机用户", id));
  }

  public Page<AppUserDto> findAppUsers(String agentId, Pageable pagination) {
    List<String> userIds = userService.getAllChildren(agentId).stream().map(user -> user.getId()).collect(Collectors.toList());
    userIds.add(agentId);

    Page<AppUserQueryDto> appUsers = appUserRepository.findAppUsers(userIds, pagination);
    Page<AppUserDto> results = appUsers.map(appUserQueryDto -> new AppUserDto(appUserQueryDto));
    return results;
  }


}
