package com.todo.springadvancetask.exception;

import com.todo.springadvancetask.exception.custom.AlreadyExistException;
import com.todo.springadvancetask.exception.custom.AuthenticationException;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

  @ExceptionHandler(AlreadyExistException.class)
  public ResponseEntity handleAlreadyExistException(AlreadyExistException e) {
    return ResponseEntity.status(e.getErrorCode()
            .getHttpStatus())
        .body(e.getErrorCode()
            .getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    String message = e.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(x -> x.getDefaultMessage())
        .collect(Collectors.joining(""));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(message);
  }
}
