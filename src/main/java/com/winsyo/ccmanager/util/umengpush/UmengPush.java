package com.winsyo.ccmanager.util.umengpush;

public class UmengPush {

  private static PushClient client = new PushClient();

  public static void sendAndroidBroadcast(String text) {
    try {
      AndroidBroadcast broadcast = new AndroidBroadcast("59c4cf07f5ade418a10000b1", "hct2ncq4hubuuk4sjdaavw4ljddlsglg");
      broadcast.setTicker("Android broadcast ticker");
      broadcast.setTitle("平台公告");
      broadcast.setText(text);
      broadcast.goAppAfterOpen();
      broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);

      // TODO Set 'production_mode' to 'false' if it's a test device.
      // For how to register a test device, please see the developer doc.
      broadcast.setProductionMode();
      // Set customized fields
      broadcast.setExtraField("test", "helloworld");
      client.send(broadcast);

    } catch (Exception e) {
      // TODO: handle exception
    }

  }

  public static void sendIOSBroadcast(String text) {

    try {

      IOSBroadcast broadcast = new IOSBroadcast("59c4cf56f29d985b98000124", "svkrr6pnh6yjvum8mcg1uyrsplget5pf");

      broadcast.setAlert("平台公告" + "\n" + text);
      broadcast.setBadge(1);
      broadcast.setSound("default");
      // TODO set 'production_mode' to 'true' if your app is under production mode
      broadcast.setTestMode();
      // Set customized fields
      broadcast.setCustomizedField("test", "helloworld");
      client.send(broadcast);

    } catch (Exception e) {
      // TODO: handle exception
    }

  }

  public static void main(String[] args) {

    // sendAndroidBroadcast("wwww");

    sendIOSBroadcast("233222");

  }

}
