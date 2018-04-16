package com.winsyo.ccmanager.service;

import com.winsyo.ccmanager.domain.Channel;
import com.winsyo.ccmanager.domain.Role;
import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.domain.UserFee;
import com.winsyo.ccmanager.domain.enumerate.ChannelType;
import com.winsyo.ccmanager.domain.enumerate.UserType;
import com.winsyo.ccmanager.repository.ChannelRepository;
import com.winsyo.ccmanager.repository.RoleRepository;
import com.winsyo.ccmanager.repository.UserFeeRepository;
import com.winsyo.ccmanager.repository.UserRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InitializationService {

  private ChannelRepository channelRepository;
  private RoleRepository roleRepository;
  private UserRepository userRepository;
  private UserFeeRepository userFeeRepository;

  public InitializationService(ChannelRepository channelRepository, RoleRepository roleRepository, UserRepository userRepository,
      UserFeeRepository userFeeRepository) {
    this.channelRepository = channelRepository;
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.userFeeRepository = userFeeRepository;
  }

  @Transactional
  public void initChannel() {
    channelRepository.deleteAll();

    // 计划通道
    Channel plan = new Channel();
    plan.setChannelType(ChannelType.PLAN);
    plan.setName("计划");
    plan.setFeeRate(new BigDecimal("0.008"));
    plan.setFee(new BigDecimal("0"));
    plan.setSeniorFeeRate(new BigDecimal("0.007"));
    plan.setSeniorFee(new BigDecimal("0"));
    plan.setPlatformFeeRate(new BigDecimal("0.0045"));
    plan.setPlatformFee(new BigDecimal("0"));
    plan.setCostFeeRate(new BigDecimal("0.0042"));
    plan.setCostFee(new BigDecimal("0"));
    plan.setDescription("计划通道");
    channelRepository.save(plan);

    // C
    Channel c = new Channel();
    c.setChannelType(ChannelType.C);
    c.setName("通道C");
    c.setFeeRate(new BigDecimal("0.0057"));
    c.setFee(new BigDecimal("2"));
    c.setSeniorFeeRate(new BigDecimal("0.0057"));
    c.setSeniorFee(new BigDecimal("2"));
    c.setPlatformFeeRate(new BigDecimal("0.0046"));
    c.setPlatformFee(new BigDecimal("0.5"));
    c.setCostFeeRate(new BigDecimal("0.0045"));
    c.setCostFee(new BigDecimal("0"));
    c.setDescription("通道C");
    channelRepository.save(c);

    // D
    Channel d = new Channel();
    d.setChannelType(ChannelType.D);
    d.setName("通道D");
    d.setFeeRate(new BigDecimal("0"));
    d.setFee(new BigDecimal("55"));
    d.setSeniorFeeRate(new BigDecimal("0"));
    d.setSeniorFee(new BigDecimal("50"));
    d.setPlatformFeeRate(new BigDecimal("0"));
    d.setPlatformFee(new BigDecimal("41"));
    d.setCostFeeRate(new BigDecimal("0"));
    d.setCostFee(new BigDecimal("38"));
    d.setDescription("通道D");
    channelRepository.save(d);

    // E
    Channel e = new Channel();
    e.setChannelType(ChannelType.E);
    e.setName("通道E");
    e.setFeeRate(new BigDecimal("0.0053"));
    e.setFee(new BigDecimal("2"));
    e.setSeniorFeeRate(new BigDecimal("0.0053"));
    e.setSeniorFee(new BigDecimal("2"));
    e.setPlatformFeeRate(new BigDecimal("0.0046"));
    e.setPlatformFee(new BigDecimal("0.5"));
    e.setCostFeeRate(new BigDecimal("0.0045"));
    e.setCostFee(new BigDecimal("0"));
    e.setDescription("通道E");
    channelRepository.save(e);

    // F
    Channel f = new Channel();
    f.setChannelType(ChannelType.F);
    f.setName("通道F");
    f.setFeeRate(new BigDecimal("0.0053"));
    f.setFee(new BigDecimal("2"));
    f.setSeniorFeeRate(new BigDecimal("0.0053"));
    f.setSeniorFee(new BigDecimal("2"));
    f.setPlatformFeeRate(new BigDecimal("0.0046"));
    f.setPlatformFee(new BigDecimal("0.5"));
    f.setCostFeeRate(new BigDecimal("0.004"));
    f.setCostFee(new BigDecimal("0"));
    f.setDescription("通道F");
    channelRepository.save(f);
  }

  @Transactional
  public void initRole() {
    Role admin = new Role();
    admin.setId(UUID.randomUUID().toString());
    admin.setName("系统管理员");
    admin.setRole("ADMIN");
    Role platform = new Role();
    platform.setId(UUID.randomUUID().toString());
    platform.setName("平台管理员");
    platform.setRole("PLATFORM");
    Role agent = new Role();
    agent.setId(UUID.randomUUID().toString());
    agent.setName("代理商");
    agent.setRole("AGENT");
    roleRepository.save(admin);
    roleRepository.save(platform);
    roleRepository.save(agent);
  }

  @Transactional
  public void initUserRole() {
    Role admin = roleRepository.findByRole("ADMIN");
    LinkedList<Role> adminList = new LinkedList<>(Arrays.asList(admin));

    Role platform = roleRepository.findByRole("PLATFORM");
    LinkedList<Role> platformList = new LinkedList<>(Arrays.asList(platform));

    Role agent = roleRepository.findByRole("AGENT");
    LinkedList<Role> agentList = new LinkedList<>(Arrays.asList(agent));

    List<User> all = userRepository.findAll();
    all.forEach((user) -> {
      switch (user.getType()) {
        case ADMIN:
          user.setRoles(adminList);
          break;

        case PLATFORM:
          user.setRoles(platformList);
          break;

        case VIRTUAL:
        case AGENT:
          user.setRoles(agentList);
          break;
      }
      userRepository.save(user);
    });
  }


  @Transactional
  public void initUserFee() {
    List<User> all = userRepository.findAll();
    List<Channel> channels = channelRepository.findAll();
    userFeeRepository.deleteAll();
    all.forEach(user -> {
      switch (user.getType()) {
        case PLATFORM:
          channels.forEach(channel -> {
            BigDecimal incomeRate = channel.getFeeRate().subtract(channel.getPlatformFeeRate());
            BigDecimal incomeFee = channel.getFee().subtract(channel.getPlatformFee());

            UserFee feeRate = new UserFee();
            feeRate.setChannelType(channel.getChannelType());
            feeRate.setUserId(user.getId());
            feeRate.setValue(incomeRate);
            feeRate.setFeeRate(true);
            userFeeRepository.save(feeRate);

            UserFee fee = new UserFee();
            fee.setChannelType(channel.getChannelType());
            fee.setUserId(user.getId());
            fee.setValue(incomeFee);
            fee.setFeeRate(false);
            userFeeRepository.save(fee);
          });
          break;

        case VIRTUAL:
        case AGENT:
          channels.forEach(channel -> {
            UserFee feeRate = new UserFee();
            feeRate.setChannelType(channel.getChannelType());
            feeRate.setUserId(user.getId());
            feeRate.setValue(new BigDecimal("0"));
            feeRate.setFeeRate(true);
            userFeeRepository.save(feeRate);

            UserFee fee = new UserFee();
            fee.setChannelType(channel.getChannelType());
            fee.setUserId(user.getId());
            fee.setValue(new BigDecimal("0"));
            fee.setFeeRate(false);
            userFeeRepository.save(fee);
          });
          break;
      }
    });
  }

  @Transactional
  public void initUserPassword() {
    List<User> all = userRepository.findAll();
    all.forEach(user -> {
      if (user.getType() != UserType.ADMIN){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode("654321"));
        userRepository.save(user);
      }
    });
  }
}
