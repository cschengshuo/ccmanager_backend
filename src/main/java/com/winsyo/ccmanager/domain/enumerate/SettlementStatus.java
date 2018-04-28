package com.winsyo.ccmanager.domain.enumerate;

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
