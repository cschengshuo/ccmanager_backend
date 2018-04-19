package com.winsyo.ccmanager.domain.enumerate;

public enum ReportType {

  TODAY(1),

  MONTH(2),

  TIME(3);

  private int index;

  ReportType(int index) {
    this.index = index;
  }

  public int index() {
    return index;
  }

  public static ReportType indexOf(int index) {
    for (ReportType item : ReportType.values()) {
      if (item.index == index) {
        return item;
      }
    }
    return null;
  }

}
