package com.todo.springadvancetask.exception;

import com.todo.springadvancetask.exception.custom.AuthorizationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

  @ExceptionHandler(AuthorizationException.class)
  public ResponseEntity handleAuthorizationException(AuthorizationException e) {
    return ResponseEntity.status(e.getErrorCode()
            .getHttpStatus())
        .body(e.getErrorCode()
            .getMessage());
  }
}
