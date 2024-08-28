package com.todo.springadvancetask.exception;

import com.todo.springadvancetask.exception.custom.AlreadyExistException;
import com.todo.springadvancetask.exception.custom.AuthenticationException;
import com.todo.springadvancetask.exception.custom.AuthorizationException;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(e.getMessage());
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<String> handleAuthorizationException(AuthenticationException e) {
    return ResponseEntity.status(e.getErrorCode()
            .getHttpStatus())
        .body(e.getErrorCode()
            .getMessage());
  }

  @ExceptionHandler(AuthorizationException.class)
  public ResponseEntity<String> handleAuthorizationException(AuthorizationException e) {
    return ResponseEntity.status(e.getErrorCode()
            .getHttpStatus())
        .body(e.getErrorCode()
            .getMessage());
  }

  @ExceptionHandler(AlreadyExistException.class)
  public ResponseEntity<String> handleAlreadyExistException(AlreadyExistException e) {
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

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(e.getMessage());
  }

}
