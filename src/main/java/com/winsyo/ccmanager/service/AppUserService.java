package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.AppUser;
import com.winsyo.ccmanager.exception.EntityNotFoundException;
import com.winsyo.ccmanager.repository.AppUserRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    AppUser appUser = appUserRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("未找到用户"));
    return appUser;
  }

  public List<AppUser> findAppUsers(String agentId) {
    Specification<AppUser> appUserSpecification = new Specification<AppUser>() {
      public Predicate toPredicate(Root<AppUser> root, CriteriaQuery<?> query,
          CriteriaBuilder builder) {
        List<Predicate> list = new ArrayList<Predicate>();

        if (StringUtils.isNotEmpty(agentId)) {
          list.add(builder.equal(root.get("agentId").as(String.class), agentId));
        }
        Predicate[] p = new Predicate[list.size()];
        return builder.and(list.toArray(p));
      }
    };
    List<AppUser> appUsers = appUserRepository.findAll(appUserSpecification);
    return appUsers;
  }


}
