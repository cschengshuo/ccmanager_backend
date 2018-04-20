package com.winsyo.ccmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.winsyo.ccmanager.domain.RechargeRecord;

@Repository
public interface RechargeRecordRepository extends JpaRepository<RechargeRecord, String>, JpaSpecificationExecutor<RechargeRecord> {

}
