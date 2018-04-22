package com.winsyo.ccmanager.util;

import com.winsyo.ccmanager.domain.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {

  public static User getCurrentUser() {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return user;
  }

  public static String getInviteCode(String s) {
    char[] c = s.toCharArray();
    List<Character> lst = new ArrayList<>();
    int num = new Random().nextInt(4);
    for (int i = 0; i < c.length; i++) {

      if (i == num) {
        c[i] = (char) (c[i] + 1);
      }

      lst.add(c[i]);
    }

    String chars = "abcdefghijklmnopqrstuvwxyz";
    char a1 = chars.charAt((int) (Math.random() * 26));
    char a2 = chars.charAt((int) (Math.random() * 26));
    System.out.println(a1);
    lst.add(a1);
    lst.add(a2);

    Collections.shuffle(lst);

    String resultStr = "";
    for (int i = 0; i < lst.size(); i++) {
      resultStr += lst.get(i);
    }
    return resultStr;
  }

}
