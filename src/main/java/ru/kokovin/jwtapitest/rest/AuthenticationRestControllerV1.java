package ru.kokovin.jwtapitest.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kokovin.jwtapitest.dto.AuthenticationRequestDto;
import ru.kokovin.jwtapitest.model.AuthenticationResponse;
import ru.kokovin.jwtapitest.model.Manager;
import ru.kokovin.jwtapitest.security.jwt.JwtTokenProvider;
import ru.kokovin.jwtapitest.service.ManagerService;

@RestController
@RequestMapping(
    value = AuthenticationRestControllerV1.REST_URL,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationRestControllerV1 {

  static final String REST_URL = "/api/v1/auth/";

  private final AuthenticationManager authenticationManager;

  private final JwtTokenProvider jwtTokenProvider;

  private final ManagerService managerService;

  /** Constructor to autowire fields. */
  @Autowired
  public AuthenticationRestControllerV1(
      AuthenticationManager authenticationManager,
      JwtTokenProvider jwtTokenProvider,
      ManagerService managerService) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
    this.managerService = managerService;
  }

  /** Login - returns JWT token with success. */
  @PostMapping(value = "login")
  public ResponseEntity<AuthenticationResponse> login(
      @RequestBody AuthenticationRequestDto requestDto) {
    try {
      String name = requestDto.getUsername();
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(name, requestDto.getPassword()));
      Manager manager = managerService.findByUserName(name);
      if (manager == null) {
        throw new UsernameNotFoundException("Manager with username " + name + " not found");
      }

      String token = jwtTokenProvider.createToken(name, manager.getRoles());
      AuthenticationResponse response = new AuthenticationResponse(token);
      return ResponseEntity.ok(response);
    } catch (AuthenticationException e) {
      throw new BadCredentialsException("Invalid username or password");
    }
  }
}
