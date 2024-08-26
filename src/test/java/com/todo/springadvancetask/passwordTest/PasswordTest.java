package com.todo.springadvancetask.passwordTest;

import com.todo.springadvancetask.config.PasswordEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PasswordTest {
  @Autowired
  PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("패스워드 테스트")
  void test1(){
    String pwd = "!@#pwd123";
    String encodedpwd = passwordEncoder.encode(pwd);
    String encodedpwd2 = passwordEncoder.encode(pwd);
    System.out.println("변환된 패스워드");
    System.out.println(encodedpwd);
    System.out.println(encodedpwd2);
    System.out.println(passwordEncoder.matches(pwd,encodedpwd));
    System.out.println(passwordEncoder.matches(pwd,encodedpwd2));
  }

}
