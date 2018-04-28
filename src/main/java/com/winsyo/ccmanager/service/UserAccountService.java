package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.UserAccount;
import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import com.winsyo.ccmanager.dto.response.WithdrawDto;
import com.winsyo.ccmanager.exception.EntityNotFoundException;
import com.winsyo.ccmanager.repository.UserAccountRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

  private UserAccountRepository userAccountRepository;
  private SettlementRecordService settlementRecordService;

  public UserAccountService(UserAccountRepository userAccountRepository, SettlementRecordService settlementRecordService) {
    this.userAccountRepository = userAccountRepository;
    this.settlementRecordService = settlementRecordService;
  }

  public UserAccount findByUserIdAndType(String userId, ChannelType type) {
    UserAccount userAccount = userAccountRepository.findByUserIdAndType(userId, type).orElseThrow(() -> new EntityNotFoundException("未找到用户账户", userId));
    return userAccount;
  }

  public List<UserAccount> findByUserId(String userId) {
    List<UserAccount> userAccount = userAccountRepository.findByUserId(userId);
    return userAccount;
  }

  @Transactional
  public UserAccount save(UserAccount userAccount) {
    UserAccount save = userAccountRepository.save(userAccount);
    return save;
  }

  public List<WithdrawDto> listSubUserAccount(String id) {

    return null;
  }
}
