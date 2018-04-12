package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.ChannelType;
import com.winsyo.ccmanager.domain.UserFee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserFeeRepository extends JpaRepository<UserFee, String>, JpaSpecificationExecutor<UserFee> {

  Optional<UserFee> findByUserIdAndChannelType(String userId, ChannelType channelType);

}
