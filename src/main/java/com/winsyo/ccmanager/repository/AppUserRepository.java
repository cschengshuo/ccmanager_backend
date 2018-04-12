package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.AppUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AppUserRepository extends JpaRepository<AppUser, String>, JpaSpecificationExecutor<AppUser> {

  List<AppUser> findAppUsersByAgentId(String agentId);

}
