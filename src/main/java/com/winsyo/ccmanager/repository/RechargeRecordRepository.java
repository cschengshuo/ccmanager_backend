package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.RechargeRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RechargeRecordRepository extends JpaRepository<RechargeRecord, String>, JpaSpecificationExecutor<RechargeRecord> {

  List<RechargeRecord> findByUserId(String userId);
}
