package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.RechargeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RechargeRecordRepository extends JpaRepository<RechargeRecord, String>, JpaSpecificationExecutor<RechargeRecord> {

}
