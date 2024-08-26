package com.todo.springadvancetask.service;

import com.todo.springadvancetask.dto.user.UserRequestDto;
import com.todo.springadvancetask.dto.user.UserResponseDto;
import com.todo.springadvancetask.entity.User;
import com.todo.springadvancetask.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public UserResponseDto createUser(UserRequestDto requestDto) {
    User user = new User(requestDto);
    User saveUser = userRepository.save(user);
    UserResponseDto responseDto = new UserResponseDto(saveUser);
    return responseDto;
  }

  public UserResponseDto findById(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow();
    return new UserResponseDto(user);
  }

  public List<UserResponseDto> findAll() {
    List<User> users = userRepository.findAll();
    return users.stream()
        .map(user -> new UserResponseDto(user))
        .toList();
  }

  @Transactional
  public UserResponseDto updateUser(Long userId, UserRequestDto requestDto) {
    User user = userRepository.findById(userId)
        .orElseThrow();
    user.setName(requestDto.getName());
    user.setEmail(requestDto.getEmail());

    return new UserResponseDto(user);
  }

  public Long deleteUser(Long userId) {
    userRepository.deleteById(userId);
    return userId;
  }
}
