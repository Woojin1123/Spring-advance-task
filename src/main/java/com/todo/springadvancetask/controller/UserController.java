package com.todo.springadvancetask.controller;

import com.todo.springadvancetask.dto.user.UserRequestDto;
import com.todo.springadvancetask.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;
  public UserController(UserService userService){
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity createUser(@RequestBody UserRequestDto requestDto){
    userService.createUser(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(1);
  }
}
