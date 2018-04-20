package com.winsyo.ccmanager.domain.enumerate;

public enum SystemConfigType {

  CHANNEL_C_DESC(20),

  CHANNEL_D_DESC(24),

  CHANNEL_E_DESC(43),

  CHANNEL_F_DESC(44);


  private int index;

  SystemConfigType(int index) {
    this.index = index;
  }

  public int index() {
    return index;
  }

  public static SystemConfigType indexOf(int index) {
    for (SystemConfigType item : SystemConfigType.values()) {
      if (item.index == index) {
        return item;
      }
    }
    return null;
  }

}
