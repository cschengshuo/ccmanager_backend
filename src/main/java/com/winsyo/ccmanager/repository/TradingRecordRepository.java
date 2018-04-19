package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.TradingRecord;
import com.winsyo.ccmanager.dto.TradingRecordQueryDto;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TradingRecordRepository extends JpaRepository<TradingRecord, String>, JpaSpecificationExecutor<TradingRecord> {

  @Query(value = "SELECT record.* FROM trading_record record WHERE record.payWayTAG IN :payWayTAG AND record.settlementStatus = :status AND record.isOK = :ok", nativeQuery = true)
  List<TradingRecord> findByPayWayTAGIsInAndSettlementStatusAndOk(@Param("payWayTAG") List<String> payWayTAG, @Param("status") boolean status, @Param("ok") boolean ok);

  @Query(value = "select new com.winsyo.ccmanager.dto.TradingRecordQueryDto(rec, app.name) from TradingRecord rec, AppUser app where rec.cardNo like :cardNo and rec.userId = app.userId and app.agentId in :agentIds order by rec.time desc")
  Page<TradingRecordQueryDto> findTradingRecords(@Param("agentIds") List<String> agentIds, @Param("cardNo") String cardNo, Pageable pageable);

  @Query(value = "select rec from TradingRecord rec, AppUser app where rec.userId = app.userId and app.agentId in :agentIds and rec.time >= :startTime and rec.time <= :endTime")
  List<TradingRecord> findTradingRecordsByTime(@Param("agentIds") List<String> agentIds, @Param("startTime") LocalDateTime start, @Param("endTime") LocalDateTime end);

  @Query(value = "select rec from TradingRecord rec, AppUser app where rec.userId = app.userId and app.agentId = :agentId and rec.time >= :startTime and rec.time <= :endTime")
  List<TradingRecord> findTradingRecordsByTime(@Param("agentId") String agentId, @Param("startTime") LocalDateTime start, @Param("endTime") LocalDateTime end);

}
