package com.todo.springadvancetask.entity;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    USER("USER"),
    ADMIN("ADMIN");

    private final String authority;
    UserRoleEnum(String authority){
      this.authority = authority;
    }
}
