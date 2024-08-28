package com.todo.springadvancetask.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  TOKEN_NOT_EXIST(HttpStatus.BAD_REQUEST, "토큰이 없습니다."),
  TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰 인증기한 만료"),
  TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
  LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "로그인 정보가 틀립니다."),
  USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "유저가 이미 존재합니다.");


  private HttpStatus httpStatus;
  private String message;
}
