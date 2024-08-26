package com.todo.springadvancetask.filter;

import com.todo.springadvancetask.entity.User;
import com.todo.springadvancetask.repository.UserRepository;
import com.todo.springadvancetask.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j(topic = "AuthFilter")
@Component
@Order(1)
public class AuthFilter implements Filter {

  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;

  public AuthFilter(JwtUtil jwtUtil, UserRepository userRepository) {
    this.jwtUtil = jwtUtil;
    this.userRepository = userRepository;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest servletRequest = (HttpServletRequest) request;
    String url = servletRequest.getRequestURI();

    if (StringUtils.hasText(url) && (url.equals("/api/users") || url.equals("/api/users/login"))) {
      chain.doFilter(request, response);
    } else {
      String tokenvalue = jwtUtil.getJwtFromHeader(servletRequest);
      if (StringUtils.hasText(tokenvalue)) {
        String token = tokenvalue;
        if (!jwtUtil.validateToken(token)) {
          throw new IllegalArgumentException("토큰 에러");
        }

        Claims info = jwtUtil.getUserInfoFromToken(token);
        User user = userRepository.findByEmail(info.getSubject())
            .orElseThrow(
                () -> new NullPointerException("유저가 존재하지 않습니다.")
            );
        request.setAttribute("user", user);
        chain.doFilter(request, response);
      } else {
        throw new IllegalArgumentException("토큰이 없습니다.");
      }
    }
  }
}
