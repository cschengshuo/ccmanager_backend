package com.winsyo.ccmanager.domain.enumerate;

import org.apache.commons.lang3.StringUtils;

public enum OK {

  TRUE("1"),

  FALSE("0");

  private String index;

  OK(String index) {
    this.index = index;
  }

  public String index() {
    return index;
  }

  public static OK indexOf(String index) {
    for (OK item : OK.values()) {
      if (StringUtils.equals(item.index, index)) {
        return item;
      }
    }
    return null;
  }
}
