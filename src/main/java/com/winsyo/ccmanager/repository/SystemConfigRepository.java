package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, String>, JpaSpecificationExecutor<SystemConfig> {


}
