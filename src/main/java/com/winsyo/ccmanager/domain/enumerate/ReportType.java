package com.winsyo.ccmanager.domain.enumerate;

/**
 * 报表类型
 */
public enum ReportType {

  /**
   * 当天
   */
  TODAY(1),

  /**
   * 当月
   */
  MONTH(2),

  /**
   * 指定时间
   */
  TIME(3),

  /**
   * 所有时间
   */
  ALL_TIME(4);

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
