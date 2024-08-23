package com.todo.springadvancetask.service;

import com.todo.springadvancetask.dto.user.UserRequestDto;
import com.todo.springadvancetask.dto.user.UserResponseDto;
import com.todo.springadvancetask.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserResponseDto createUser(UserRequestDto requestDto) {
    return null;
  }
}
