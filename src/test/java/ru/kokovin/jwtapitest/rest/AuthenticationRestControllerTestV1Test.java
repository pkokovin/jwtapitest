package ru.kokovin.jwtapitest.rest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import ru.kokovin.jwtapitest.model.AuthenticationResponse;

class AuthenticationRestControllerTestV1Test extends AbstractRestControllerTest {

  @Test
  void loginWithSuccess() {

    AuthenticationResponse authenticationResponse = getAuthHeadersForManager("admin", "test");

    assertNotNull(authenticationResponse.getJwt());
  }

  @Test
  void loginWithWrongPass() {

    AuthenticationResponse authenticationResponse = getAuthHeadersForManager("admin", "test2");

    assertNull(authenticationResponse.getJwt());
  }
}
