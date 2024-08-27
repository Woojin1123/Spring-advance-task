package com.todo.springadvancetask.exception;

import com.todo.springadvancetask.exception.custom.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity handleAuthorizationException(AuthenticationException e) {
    return ResponseEntity.status(e.getErrorCode()
            .getHttpStatus())
        .body(e.getErrorCode()
            .getMessage());
  }
}
