package com.winsyo.ccmanager.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.winsyo.ccmanager.domain.AgentApply;
import com.winsyo.ccmanager.service.AgentApplyService;
import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 代理申请
 */
@RestController
@RequestMapping(value = "/agent_apply")
public class AgentApplyController {

  private AgentApplyService agentApplyService;

  public AgentApplyController(AgentApplyService agentApplyService) {
    this.agentApplyService = agentApplyService;
  }

  /**
   * 获取所有代理申请列表
   * @param page
   * @param size
   * @return
   */
  @GetMapping(value = "findAll")
  public ResponseEntity findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
    PageRequest pagination = PageRequest.of(page, size);
    Page<AgentApply> agentApplies = agentApplyService.findAll(pagination);
    return ok(agentApplies);
  }

}
