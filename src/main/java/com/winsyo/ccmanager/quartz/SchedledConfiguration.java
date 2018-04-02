package com.winsyo.ccmanager.quartz;

import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class SchedledConfiguration {

  @Bean
  public MethodInvokingJobDetailFactoryBean generateDailyReportJob() {
    MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
    bean.setConcurrent(false);
    bean.setTargetBeanName("userService");
    bean.setTargetMethod("testJob");
    return bean;
  }

  @Bean
  public CronTriggerFactoryBean generateDailyReportTrigger(@Qualifier("generateDailyReportJob") MethodInvokingJobDetailFactoryBean bean) {
    CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
    trigger.setJobDetail(bean.getObject());
    trigger.setCronExpression("0 0/1 * * * ? ");//每一分钟执行一次
    return trigger;
  }

  @Bean
  public MethodInvokingJobDetailFactoryBean settleJob() {
    MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
    bean.setConcurrent(false);
    bean.setTargetBeanName("userService");
    bean.setTargetMethod("testJob2");
    return bean;
  }

  @Bean
  public CronTriggerFactoryBean settleTrigger(@Qualifier("settleJob") MethodInvokingJobDetailFactoryBean bean) {
    CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
    trigger.setJobDetail(bean.getObject());
    trigger.setCronExpression("0 0 2 * * ? ");
    return trigger;
  }

  @Bean
  public SchedulerFactoryBean schedulerFactoryBean(Trigger... triggers) {
    SchedulerFactoryBean bean = new SchedulerFactoryBean();
    bean.setStartupDelay(5);
    bean.setTriggers(triggers);
    return bean;
  }

}
