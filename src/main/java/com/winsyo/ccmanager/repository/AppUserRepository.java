package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.AppUser;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String>, JpaSpecificationExecutor<AppUser> {

  @Query(value = "select sum(canbalance) from AppUser")
  BigDecimal findAppUserWithdrawSumMoney();

  Optional<AppUser> findByContactPhone(String contactPhone);

  List<AppUser> findAppUsersByRecommendUserId(String recommendUserId);

}
