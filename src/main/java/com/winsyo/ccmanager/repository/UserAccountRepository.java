package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.UserAccount;
import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String>, JpaSpecificationExecutor<UserAccount> {

  Optional<UserAccount> findByUserIdAndType(String userId, ChannelType type);

  List<UserAccount> findByUserId(String userId);

}
