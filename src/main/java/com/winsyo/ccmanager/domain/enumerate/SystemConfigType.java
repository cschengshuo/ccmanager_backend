package com.winsyo.ccmanager.domain.enumerate;

/**
 * 系统设置类型
 *
 * @see com.winsyo.ccmanager.domain.SystemConfig
 */
public enum SystemConfigType {

  /**
   * 通道C描述
   */
  CHANNEL_C_DESC(20),

  /**
   * 通道D描述
   */
  CHANNEL_D_DESC(24),

  /**
   * 通道E描述
   */
  CHANNEL_E_DESC(43),

  /**
   * 通道F描述
   */
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
