package com.winsyo.ccmanager.service;

import static org.junit.Assert.*;

import com.winsyo.ccmanager.domain.User;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

  @Autowired
  private UserService userService;

    @Test
  public void getParentQueue() {
    List<User> queue = userService.getParentQueue("1d15dae403f4418a9c9516d5e6a6576c");
    queue.forEach((user)->{
      System.out.println(user);
    });
  }
}