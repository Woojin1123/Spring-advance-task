package com.todo.springadvancetask.service;

import com.todo.springadvancetask.config.PasswordEncoder;
import com.todo.springadvancetask.dto.user.UserRequestDto;
import com.todo.springadvancetask.dto.user.UserResponseDto;
import com.todo.springadvancetask.entity.User;
import com.todo.springadvancetask.repository.UserRepository;
import com.todo.springadvancetask.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  @Transactional
  public UserResponseDto createUser(UserRequestDto requestDto, HttpServletResponse res) {
    User user = new User(requestDto);
    user.setPwd(passwordEncoder.encode(requestDto.getPwd()));
    User saveUser = userRepository.save(user);
    String token = jwtUtil.createToken(saveUser.getEmail());
    jwtUtil.addJwtToCookie(token,res);
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
