package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.AgentApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentApplyRepository  extends JpaRepository<AgentApply, String>, JpaSpecificationExecutor<AgentApply> {



}
