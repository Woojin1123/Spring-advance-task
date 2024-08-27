package com.todo.springadvancetask.controller;

import com.todo.springadvancetask.dto.user.UserRequestDto;
import com.todo.springadvancetask.dto.user.UserResponseDto;
import com.todo.springadvancetask.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto requestDto,
      HttpServletResponse res) {
    UserResponseDto responseDto = userService.createUser(requestDto,res);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(responseDto);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserResponseDto> findById(@PathVariable Long userId) {
    UserResponseDto responseDto = userService.findById(userId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(responseDto);
  }

  @GetMapping
  public ResponseEntity<List<UserResponseDto>> findAll() {
    List<UserResponseDto> responseDtos = userService.findAll();
    return ResponseEntity.status(HttpStatus.OK)
        .body(responseDtos);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId,
      @RequestBody UserRequestDto requestDto) {
    UserResponseDto responseDto = userService.updateUser(userId, requestDto);
    return ResponseEntity.status(HttpStatus.OK)
        .body(responseDto);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Long> deleteUser(@PathVariable Long userId) {
    Long deletedId = userService.deleteUser(userId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(userId);
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody UserRequestDto requestDto, HttpServletResponse res){
    userService.login(requestDto,res);
    return ResponseEntity.status(HttpStatus.OK).body("로그인 성공");
  }
}
