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

class AppErrorRestControllerTestV1Test extends AbstractRestControllerTest {
  private static final String MANAGERS_GET_URL = "/api/v1/admin/managers/";
  private static final String GET_LAST_ERROR_URL = "/error/last";

  @Autowired private AppErrorRepository repository;

  @AfterEach
  public void resetDb() {
    repository.deleteAll();
  }

  @Test
  void getLatestErrorWithSuccess() {

    AuthenticationResponse authenticationResponse = getAuthHeadersForManager("admin", "test");

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer_" + authenticationResponse.getJwt());

    ResponseEntity<String> response =
        restTemplate.exchange(
            MANAGERS_GET_URL + "-1", HttpMethod.GET, new HttpEntity<>(headers), String.class);

    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    response =
        restTemplate.exchange(
            GET_LAST_ERROR_URL, HttpMethod.GET, new HttpEntity<>(headers), String.class);

    assertTrue(response.getBody().contains("There is no manager with id -1"));
    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }

  @Test
  void getLatestErrorUnauthorized() {

    HttpHeaders headers = new HttpHeaders();

    ResponseEntity<String> response =
        restTemplate.exchange(
            GET_LAST_ERROR_URL, HttpMethod.GET, new HttpEntity<>(headers), String.class);

    assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
  }
}
