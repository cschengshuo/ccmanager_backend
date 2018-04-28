package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.SettlementRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SettlementRecordRepository extends JpaRepository<SettlementRecord, String>, JpaSpecificationExecutor<SettlementRecord> {

}
