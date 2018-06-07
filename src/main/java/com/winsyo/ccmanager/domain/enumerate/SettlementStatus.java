package com.winsyo.ccmanager.domain.enumerate;

/**
 * 提现记录状态
 */
public enum SettlementStatus {

  PENDING(1),

  ACCEPT(2),

  REJECT(3);

  private int index;

  SettlementStatus(int index) {
    this.index = index;
  }

  public int index() {
    return index;
  }

  public static SettlementStatus indexOf(int index) {
    for (SettlementStatus item : SettlementStatus.values()) {
      if (item.index == index) {
        return item;
      }
    }
    return null;
  }

}
