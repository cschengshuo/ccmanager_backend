package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.TradingRecord;
import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TradingRecordRepository extends JpaRepository<TradingRecord, String>, JpaSpecificationExecutor<TradingRecord> {


  @Query(value = "SELECT record.* FROM trading_record record WHERE record.payWayTAG IN :payWayTAG AND record.settlementStatus = :status AND record.isOK = :ok", nativeQuery = true)
  List<TradingRecord> findByPayWayTAGIsInAndSettlementStatusAndOk(@Param("payWayTAG") List<String> payWayTAG, @Param("status") boolean status, @Param("ok") boolean ok);


}
