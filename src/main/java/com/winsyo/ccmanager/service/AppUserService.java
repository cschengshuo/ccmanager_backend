package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.AppUser;
import com.winsyo.ccmanager.exception.EntityNotFoundException;
import com.winsyo.ccmanager.repository.AppUserRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

  private AppUserRepository appUserRepository;

  @Autowired
  public AppUserService(AppUserRepository appUserRepository) {
    this.appUserRepository = appUserRepository;
  }

  public AppUser findById(String id) {
    return appUserRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("未找到该手机用户"));
  }

  public List<AppUser> findAppUsers(String agentId) {
    Specification<AppUser> appUserSpecification = (Specification<AppUser>) (root, query, builder) -> {
      List<Predicate> list = new ArrayList<>();

      if (StringUtils.isNotEmpty(agentId)) {
        list.add(builder.equal(root.get("agentId").as(String.class), agentId));
      }
      Predicate[] p = new Predicate[list.size()];
      return builder.and(list.toArray(p));
    };
    List<AppUser> appUsers = appUserRepository.findAll(appUserSpecification);
    return appUsers;
  }


}
