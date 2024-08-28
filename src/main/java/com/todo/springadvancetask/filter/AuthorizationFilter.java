package com.todo.springadvancetask.filter;

import com.todo.springadvancetask.entity.User;
import com.todo.springadvancetask.entity.UserRoleEnum;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j(topic = "Authorization")
@Component
@Order(2)
public class AuthorizationFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest servletRequest = (HttpServletRequest) request;
    String url = servletRequest.getRequestURI();
    String method = servletRequest.getMethod();
    if (!isAdminUrl(url, method)) {
      chain.doFilter(request, response); // 로그인 & 유저등록은 제외
    }else {
      User user = (User) servletRequest.getAttribute("user");
      String role = (String) servletRequest.getAttribute("role");
      if (user.getRole()
          .equals(UserRoleEnum.ADMIN.getAuthority()) && role.equals(
          UserRoleEnum.ADMIN.getAuthority())) {
        chain.doFilter(request, response);
        return;
      } else {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setStatus(403);
        res.setCharacterEncoding("utf-8");
        res.getWriter()
            .write("권한이 없습니다.");
      }
    }
  }

  private boolean isAdminUrl(String url, String method) {
    List<String> cud = Arrays.asList("DELETE", "PUT", "POST");
    return StringUtils.hasText(url) && (url.startsWith("/api/users") && cud.contains(method)) && !(url.equals("/api/users") || url.equals("/api/users/login"));
  }
}
