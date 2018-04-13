package com.winsyo.ccmanager.domain.enumerate;

public enum ChannelType {

  PLAN(0),

  C(3),

  D(4),

  E(5),

  AUTH(6),

  F(7);

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
}
