package com.winsyo.ccmanager.domain.enumerate;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * 通道类型
 */
public enum ChannelType {

  /**
   * 计划
   */
  PLAN(0),

  /**
   * 新C通道
   */
  C(3),

  /**
   * D通道
   */
  D(4),

  /**
   * E通道
   */
  E(5),

  /**
   * 认证
   */
  AUTH(6),

  /**
   * F通道
   */
  F(7),

  /**
   * 用户提现记录
   */
  WITHDRAW(8);

  private int index;

  ChannelType(int index) {
    this.index = index;
  }

  public int index() {
    return index;
  }

  public static ChannelType indexOf(int index) {
    for (ChannelType item : ChannelType.values()) {
      if (item.index == index) {
        return item;
      }
    }
    return null;
  }

  public static ChannelType indexOf(PayWayTag payWayTag) {
    Integer integer = NumberUtils.createInteger(payWayTag.index());
    for (ChannelType item : ChannelType.values()) {
      if (item.index == integer) {
        return item;
      }
    }
    return null;
  }
}
