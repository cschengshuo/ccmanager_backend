package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.AgentApply;
import com.winsyo.ccmanager.repository.AgentApplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AgentApplyService {

  private AgentApplyRepository agentApplyRepository;

  public AgentApplyService(AgentApplyRepository agentApplyRepository) {
    this.agentApplyRepository = agentApplyRepository;
  }

  public Page<AgentApply> findAll(Pageable pageable) {
    Page<AgentApply> applies = agentApplyRepository.findAll(pageable);
    return applies;
  }

}
