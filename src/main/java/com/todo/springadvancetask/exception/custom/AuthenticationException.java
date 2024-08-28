package com.todo.springadvancetask.exception.custom;

import com.todo.springadvancetask.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class AuthenticationException extends RuntimeException {

  private final ErrorCode errorCode;
}
