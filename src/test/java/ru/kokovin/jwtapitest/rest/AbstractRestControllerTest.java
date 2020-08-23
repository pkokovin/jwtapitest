package ru.kokovin.jwtapitest.rest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.kokovin.jwtapitest.model.AuthenticationRequest;
import ru.kokovin.jwtapitest.model.AuthenticationResponse;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractRestControllerTest {
  public static String LOGIN_URL = "/api/v1/auth/login";

  @Autowired protected TestRestTemplate restTemplate;

  protected AuthenticationResponse getAuthHeadersForManager(String name, String password) {

    AuthenticationRequest authenticationRequest = new AuthenticationRequest();
    authenticationRequest.setUsername(name);
    authenticationRequest.setPassword(password);
    return restTemplate.postForObject(
        LOGIN_URL, authenticationRequest, AuthenticationResponse.class);
  }
}
