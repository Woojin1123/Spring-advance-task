package com.todo.springadvancetask.config;

import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class Base64Config {
  private final String salt = "secretsecretsc";
  public String encode(String plainKey){
    String secretKey = plainKey + salt;
    return Base64.getEncoder().encodeToString(secretKey.getBytes());
  }
}
