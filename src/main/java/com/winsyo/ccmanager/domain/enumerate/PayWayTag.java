package com.winsyo.ccmanager.domain.enumerate;

public enum  PayWayTag {

  /**
   * 计划
   */
  PLAN("0"),

  /**
   * 新C通道
   */
  C("3"),

  /**
   * D通道
   */
  D("4"),

  /**
   * E通道
   */
  E("5"),

  /**
   * 认证
   */
  AUTH("6"),

  /**
   * F通道
   */
  F("7"),

  /**
   * 用户提现记录
   */
  WITHDRAW("8");

  private String index;

  PayWayTag(String index) {
    this.index = index;
  }

  public String index() {
    return index;
  }

  public static PayWayTag indexOf(String index) {
    for (PayWayTag item : PayWayTag.values()) {
      if (item.index.equals(index)) {
        return item;
      }
    }
    return null;
  }

}
