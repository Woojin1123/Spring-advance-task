package com.todo.springadvancetask.service;

import com.todo.springadvancetask.config.PasswordEncoder;
import com.todo.springadvancetask.dto.user.UserRequestDto;
import com.todo.springadvancetask.dto.user.UserResponseDto;
import com.todo.springadvancetask.entity.User;
import com.todo.springadvancetask.entity.UserRoleEnum;
import com.todo.springadvancetask.exception.custom.AlreadyExistException;
import com.todo.springadvancetask.exception.ErrorCode;
import com.todo.springadvancetask.exception.custom.AuthenticationException;
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
    userRepository.findByEmail(requestDto.getEmail())
        .ifPresent(a -> {
          throw new AlreadyExistException(ErrorCode.USER_ALREADY_EXIST);
        }); //중복 검사
    User user = new User(requestDto);
    user.setPwd(passwordEncoder.encode(requestDto.getPwd()));
    if (userRepository.findAll()
        .isEmpty()) {
      user.setRole(UserRoleEnum.ADMIN.getAuthority());
    } else {
      user.setRole(UserRoleEnum.USER.getAuthority());
    }

    User saveUser = userRepository.save(user);
    String token = jwtUtil.createToken(user.getEmail(), user.getRole());
    res.addHeader("Authorization", token);
    jwtUtil.addJwtToCookie(token, res);

    return new UserResponseDto(saveUser);
  }

  public UserResponseDto findById(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow();
    return new UserResponseDto(user);
  }

  public List<UserResponseDto> findAll() {
    List<User> users = userRepository.findAll();
    return users.stream()
        .map(UserResponseDto::new)
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

  public void login(UserRequestDto requestDto, HttpServletResponse res) {
    String email = requestDto.getEmail();
    String pwd = requestDto.getPwd();
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new NullPointerException("해당 사용자가 없습니다."));
    if (!passwordEncoder.matches(pwd, user.getPwd())) {
      throw new AuthenticationException(ErrorCode.LOGIN_FAILED);
    }
    String token = jwtUtil.createToken(user.getEmail(), user.getRole());
    res.addHeader("Authorization", token);
    jwtUtil.addJwtToCookie(token, res);
  }
}
