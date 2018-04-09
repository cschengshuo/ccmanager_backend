package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.TradingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TradingRecordRepository extends JpaRepository<TradingRecord, String>, JpaSpecificationExecutor<TradingRecord> {

}
