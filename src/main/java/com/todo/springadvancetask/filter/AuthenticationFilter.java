package com.todo.springadvancetask.filter;

import com.todo.springadvancetask.entity.User;
import com.todo.springadvancetask.service.UserService;
import com.todo.springadvancetask.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j(topic = "Authentication")
@Component
@Order(1)
public class AuthenticationFilter implements Filter {

  private final JwtUtil jwtUtil;
  private final UserService userService;

  public AuthenticationFilter(JwtUtil jwtUtil, UserService userService) {
    this.jwtUtil = jwtUtil;
    this.userService = userService;

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest servletRequest = (HttpServletRequest) request;
    String url = servletRequest.getRequestURI();

    if (StringUtils.hasText(url) && (url.equals("/api/users") || url.equals("/api/users/login"))) {
      chain.doFilter(request, response); // 로그인 & 유저등록은 제외
    } else {
      try { //유저 인증
        String token = jwtUtil.getJwtFromCookie(servletRequest);
        if (StringUtils.hasText(token)) {
          jwtUtil.validateToken(token);
          Claims info = jwtUtil.getUserInfoFromToken(token);
          User user = userService.findUserByEmail(info.getSubject())
              .orElseThrow(() -> new NullPointerException("유저가 존재하지 않습니다."));
          request.setAttribute("user", user);
          request.setAttribute("role", info.get("role"));
          chain.doFilter(request, response);
        } else {
          HttpServletResponse res = (HttpServletResponse) response;
          res.setStatus(400);
          res.setCharacterEncoding("utf-8");
          res.getWriter()
              .write("토큰이 존재하지 않습니다.");
        }
      } catch (ExpiredJwtException e) {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setStatus(401);
        res.setCharacterEncoding("utf-8");
        res.getWriter()
            .write("토큰이 만료되었습니다.");
      } catch (NullPointerException e) {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setStatus(404);
        res.setCharacterEncoding("utf-8");
        res.getWriter()
            .write(e.getMessage());
      }
    }
  }
}
