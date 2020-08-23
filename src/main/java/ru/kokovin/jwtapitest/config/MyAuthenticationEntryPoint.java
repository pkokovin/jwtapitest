package ru.kokovin.jwtapitest.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    // 401
    final String expired = (String) request.getAttribute("expired");
    log.info(expired);
    if (expired != null) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization Failed : " + expired);
    } else {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
    }
  }

  /** Change response to 401 not 403. */
  @ExceptionHandler(value = {AccessDeniedException.class})
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException {
    // 401
    response.sendError(
        HttpServletResponse.SC_UNAUTHORIZED,
        "Authorization Failed : " + accessDeniedException.getMessage());
  }
}
