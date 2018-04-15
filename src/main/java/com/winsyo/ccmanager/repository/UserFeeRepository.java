package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.UserFee;
import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFeeRepository extends JpaRepository<UserFee, String>, JpaSpecificationExecutor<UserFee> {

  Optional<UserFee> findByUserIdAndChannelTypeAndFeeRate(String userId, ChannelType channelType, boolean feeRate);

}
