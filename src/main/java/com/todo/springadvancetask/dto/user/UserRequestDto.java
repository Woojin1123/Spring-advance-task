package com.todo.springadvancetask.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRequestDto {

  @NotBlank(message = "이름을 입력해주세요")
  private String name;
  @NotBlank(message = "이메일을 입력해주세요.")
  @Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$", message = "이메일 형식이 다릅니다.")
  private String email;
  @Size(min = 8, max = 64, message = "비밀번호는 8자 이상 64자 이하로 입력해주세요.")
  private String pwd;
  private String role;
}
