package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.Channel;
import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, String>, JpaSpecificationExecutor<Channel> {

  Optional<Channel> findByChannelType(ChannelType type);

}
