package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.AppUser;
import com.winsyo.ccmanager.dto.query.AppUserQueryDto;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String>, JpaSpecificationExecutor<AppUser> {

  List<AppUser> findAppUsersByAgentId(String agentId);

  @Query(value = "select new com.winsyo.ccmanager.dto.query.AppUserQueryDto(app, u.name) from AppUser app, User u where app.agentId = u.id and app.agentId in :agentIds")
  Page<AppUserQueryDto> findAppUsers(@Param("agentIds") List<String> agentIds, Pageable pageable);


  @Query(value = "select sum(canbalance) from AppUser")
  BigDecimal findAppUserWithdrawSumMoney();

}
