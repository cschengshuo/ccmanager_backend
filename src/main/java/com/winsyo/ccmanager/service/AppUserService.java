package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.AppUser;
import com.winsyo.ccmanager.exception.EntityNotFoundException;
import com.winsyo.ccmanager.repository.AppUserRepository;
import com.winsyo.ccmanager.util.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
    return appUserRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("未找到该手机用户"));
  }

  public List<AppUser> findAppUsers(String agentId) {
    List<String> userIds = userService.getAllChildren(agentId).stream().map(user -> user.getId()).collect(Collectors.toList());
    userIds.add(agentId);

    Specification<AppUser> appUserSpecification = (Specification<AppUser>) (root, query, builder) -> {
      List<Predicate> list = new ArrayList<>();

      list.add(root.get("agentId").as(String.class).in(userIds));
      Predicate[] p = new Predicate[list.size()];
      return builder.and(list.toArray(p));
    };
    List<AppUser> appUsers = appUserRepository.findAll(appUserSpecification);
    return appUsers;
  }


}
