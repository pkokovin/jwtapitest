package ru.kokovin.jwtapitest.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.kokovin.jwtapitest.model.AuthenticationResponse;
import ru.kokovin.jwtapitest.repository.AppErrorRepository;

class AdminRestControllerTestV1Test extends AbstractRestControllerTest {
  private static final String MANAGERS_GET_URL = "/api/v1/admin/managers/";

  @Autowired
  private AppErrorRepository repository;

  @AfterEach
  public void resetDb() {
    repository.deleteAll();
  }

  @Test
  void getManagerByIdSuccess() {
    AuthenticationResponse authenticationResponse = getAuthHeadersForManager("admin", "test");

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer_" + authenticationResponse.getJwt());

    ResponseEntity<String> response =
        restTemplate.exchange(
            MANAGERS_GET_URL + 1, HttpMethod.GET, new HttpEntity<>(headers), String.class);

    assertTrue(response.getBody().contains("admin"));
    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }

  @Test
  void getManagerWithWrongId() {
    AuthenticationResponse authenticationResponse = getAuthHeadersForManager("admin", "test");

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer_" + authenticationResponse.getJwt());

    ResponseEntity<String> response =
        restTemplate.exchange(
            MANAGERS_GET_URL + "-1", HttpMethod.GET, new HttpEntity<>(headers), String.class);

    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
  }

  @Test
  void getUnauthorized() {
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<String> response =
        restTemplate.exchange(
            MANAGERS_GET_URL + 1, HttpMethod.GET, new HttpEntity<>(headers), String.class);
    assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
  }
}
