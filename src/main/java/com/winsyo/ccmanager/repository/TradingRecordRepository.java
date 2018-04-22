package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.TradingRecord;
import com.winsyo.ccmanager.domain.enumerate.PayWayTag;
import com.winsyo.ccmanager.dto.query.TradingRecordQueryDto;
import java.math.BigDecimal;
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

  List<TradingRecord> findByPayWayTAGIsInAndSettlementStatusAndOkAndType(List<PayWayTag> payWayTAGs, boolean status, boolean ok, String type);

  @Query(value = "select new com.winsyo.ccmanager.dto.query.TradingRecordQueryDto(rec, app.name) from TradingRecord rec, AppUser app where rec.cardNo like :cardNo and rec.userId = app.userId and app.agentId in :agentIds order by rec.time desc")
  Page<TradingRecordQueryDto> findTradingRecords(@Param("agentIds") List<String> agentIds, @Param("cardNo") String cardNo, Pageable pageable);

  @Query(value = "select new com.winsyo.ccmanager.dto.query.TradingRecordQueryDto(rec, app.name) from TradingRecord rec, AppUser app where rec.userId = app.userId and app.agentId in :agentIds and rec.payWayTAG = :payWayTAG order by rec.time desc")
  Page<TradingRecordQueryDto> findTradingRecordsByPayWayTag(@Param("agentIds") List<String> agentIds, @Param("payWayTAG") PayWayTag payWayTAG, Pageable pageable);

  @Query(value = "select rec from TradingRecord rec, AppUser app where rec.userId = app.userId and app.agentId in :agentIds and rec.type = '0' and rec.time >= :startTime and rec.time <= :endTime")
  List<TradingRecord> findTradingRecordsByTime(@Param("agentIds") List<String> agentIds, @Param("startTime") LocalDateTime start, @Param("endTime") LocalDateTime end);

  @Query(value = "select rec from TradingRecord rec, AppUser app where rec.userId = app.userId and app.agentId = :agentId and rec.type = '0' and rec.time >= :startTime and rec.time <= :endTime")
  List<TradingRecord> findTradingRecordsByTime(@Param("agentId") String agentId, @Param("startTime") LocalDateTime start, @Param("endTime") LocalDateTime end);


  @Query(value = "select sum(money) from TradingRecord where payWayTAG = :payWayTAG and ok=:ok ")
  BigDecimal findAppUserHasWithdrawedSumMoney(@Param("payWayTAG") PayWayTag payWayTAG, @Param("ok") boolean ok);

}
