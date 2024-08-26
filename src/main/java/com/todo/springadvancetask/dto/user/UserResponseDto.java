package com.todo.springadvancetask.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.todo.springadvancetask.entity.User;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
    this.createdAt = saveUser.getCreatedAt()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    this.updatedAt = saveUser.getUpdatedAt()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }

  public UserResponseDto(Long id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }
}
