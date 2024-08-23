package com.todo.springadvancetask.dto.user;

import com.todo.springadvancetask.entity.User;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class UserResponseDto {
  private Long id;
  private String name;
  private String email;
  private String createdAt;
  private String updatedAt;
  public UserResponseDto(User saveUser) {
    this.id = saveUser.getId();
    this.name = saveUser.getName();
    this.email = saveUser.getEmail();
    this.createdAt = saveUser.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    this.updatedAt = saveUser.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }
}
