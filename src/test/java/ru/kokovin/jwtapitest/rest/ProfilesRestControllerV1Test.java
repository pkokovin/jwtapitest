package ru.kokovin.jwtapitest.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.kokovin.jwtapitest.UserTestData.EMAIL_REQUEST_DTO_USER_DTO_1;
import static ru.kokovin.jwtapitest.UserTestData.EMAIL_REQUEST_DTO_USER_NOT_EXIST;
import static ru.kokovin.jwtapitest.UserTestData.EMAIL_REQUEST_DTO_USER_UPPERCASE;
import static ru.kokovin.jwtapitest.UserTestData.INCORRECT_EMAIL_FORMAT_USER_DTO;
import static ru.kokovin.jwtapitest.UserTestData.USER_DTO_1;
import static ru.kokovin.jwtapitest.UserTestData.USER_DTO_2;
import static ru.kokovin.jwtapitest.UserTestData.USER_DTO_DUBLICATE_EMAIL_2;
import static ru.kokovin.jwtapitest.UserTestData.USER_DTO_WITH_DIFF_CASE_EMAIL;

import java.util.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ru.kokovin.jwtapitest.dto.EmailRequestDto;
import ru.kokovin.jwtapitest.dto.UserDto;
import ru.kokovin.jwtapitest.dto.UserResponseDto;
import ru.kokovin.jwtapitest.model.AuthenticationResponse;
import ru.kokovin.jwtapitest.model.User;
import ru.kokovin.jwtapitest.repository.AppErrorRepository;
import ru.kokovin.jwtapitest.repository.UserRepository;

class ProfilesRestControllerV1Test extends AbstractRestControllerTest {
  private static final String USERS_GET_URL = "/profiles";
  private static final String USERS_SET_URL = "/profiles/set";
  private static final String USER_GET_BY_EMAIL = "/profiles/get";
  private static final String USER_GET_BY_ID = "/profiles/";
  private static final String USER_GET_LAST = "/profiles/last";

  @Autowired private UserRepository repository;
  @Autowired private AppErrorRepository appErrorRepository;

  @AfterEach
  public void resetDb() {
    repository.deleteAll();
    appErrorRepository.deleteAll();
  }

  @Test
  void getAllWithSuccess() {
    AuthenticationResponse authenticationResponse = getAuthHeadersForManager("admin", "test");

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer_" + authenticationResponse.getJwt());

    ResponseEntity<String> response =
        restTemplate.exchange(
            USERS_GET_URL, HttpMethod.GET, new HttpEntity<>(headers), String.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }

  @Test
  void getAllUnauthorized() {
    HttpHeaders headers = new HttpHeaders();

    ResponseEntity<String> response =
        restTemplate.exchange(
            USERS_GET_URL, HttpMethod.GET, new HttpEntity<>(headers), String.class);

    assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
  }

  @Test
  void setWithSuccess() {
    AuthenticationResponse authenticationResponse = getAuthHeadersForManager("admin", "test");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add("Authorization", "Bearer_" + authenticationResponse.getJwt());

    HttpEntity<UserDto> request = new HttpEntity<>(USER_DTO_1, headers);

    ResponseEntity<UserResponseDto> response =
        restTemplate.postForEntity(USERS_SET_URL, request, UserResponseDto.class);
    User user = null;
    if (response.getBody() != null && response.getBody().getIdUser() != null) {
      user = repository.findById(response.getBody().getIdUser()).orElse(null);
    }
    assertNotNull(user);
    assertEquals(user.getName(), USER_DTO_1.getName().toLowerCase());
    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }

  @Test
  void setWithDifferentCaseOfName() {
    AuthenticationResponse authenticationResponse = getAuthHeadersForManager("admin", "test");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add("Authorization", "Bearer_" + authenticationResponse.getJwt());

    HttpEntity<UserDto> request = new HttpEntity<>(USER_DTO_1, headers);

    ResponseEntity<UserResponseDto> response =
        restTemplate.postForEntity(USERS_SET_URL, request, UserResponseDto.class);
    User user = null;
    if (response.getBody() != null && response.getBody().getIdUser() != null) {
      user = repository.findById(response.getBody().getIdUser()).orElse(null);
    }
    assertNotNull(user);
    assertEquals(user.getName(), USER_DTO_1.getName().toLowerCase());
    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }

  @Test
  void setWithWrongFormatEmail() {
    AuthenticationResponse authenticationResponse = getAuthHeadersForManager("admin", "test");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add("Authorization", "Bearer_" + authenticationResponse.getJwt());

    HttpEntity<UserDto> request = new HttpEntity<>(INCORRECT_EMAIL_FORMAT_USER_DTO, headers);

    ResponseEntity<String> response =
        restTemplate.postForEntity(USERS_SET_URL, request, String.class);
    if (response.getBody() != null) {
      assertTrue(response.getBody().contains("Wrong email format"));
    }
    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
  }

  @Test
  void setWithDifferentCaseEmail() {
    AuthenticationResponse authenticationResponse = getAuthHeadersForManager("admin", "test");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add("Authorization", "Bearer_" + authenticationResponse.getJwt());

    HttpEntity<UserDto> request = new HttpEntity<>(USER_DTO_WITH_DIFF_CASE_EMAIL, headers);

    ResponseEntity<UserResponseDto> response =
        restTemplate.postForEntity(USERS_SET_URL, request, UserResponseDto.class);
    User user = null;
    if (response.getBody() != null && response.getBody().getIdUser() != null) {
      user = repository.findById(response.getBody().getIdUser()).orElse(null);
    }
    assertNotNull(user);
    assertEquals(user.getEmail(), USER_DTO_WITH_DIFF_CASE_EMAIL.getEmail().toLowerCase());
    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }

  @Test
  void setWithExistingEmail() {
    AuthenticationResponse authenticationResponse = getAuthHeadersForManager("admin", "test");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add("Authorization", "Bearer_" + authenticationResponse.getJwt());

    HttpEntity<UserDto> request = new HttpEntity<>(USER_DTO_2, headers);

    ResponseEntity<UserResponseDto> response =
        restTemplate.postForEntity(USERS_SET_URL, request, UserResponseDto.class);
    User user = null;
    if (response.getBody() != null && response.getBody().getIdUser() != null) {
      user = repository.findById(response.getBody().getIdUser()).orElse(null);
    }
    assertNotNull(user);
    assertEquals(user.getName(), USER_DTO_2.getName().toLowerCase());
    assertEquals(response.getStatusCode(), HttpStatus.OK);

    HttpEntity<UserDto> request2 = new HttpEntity<>(USER_DTO_DUBLICATE_EMAIL_2, headers);
    ResponseEntity<String> response2 =
        restTemplate.postForEntity(USERS_SET_URL, request2, String.class);
    if (response2.getBody() != null) {
      assertTrue(response2.getBody().contains("Such email alredy exists"));
    }
    assertEquals(response2.getStatusCode(), HttpStatus.CONFLICT);
  }

  @Test
  void setUnauthorized() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    HttpEntity<UserDto> request = new HttpEntity<>(USER_DTO_1, headers);

    ResponseEntity<UserResponseDto> response =
        restTemplate.postForEntity(USERS_SET_URL, request, UserResponseDto.class);

    assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
  }

  @Test
  void getByIdWithSuccess() {
    AuthenticationResponse authenticationResponse = getAuthHeadersForManager("admin", "test");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add("Authorization", "Bearer_" + authenticationResponse.getJwt());

    HttpEntity<UserDto> request = new HttpEntity<>(USER_DTO_1, headers);

    ResponseEntity<UserResponseDto> response =
        restTemplate.postForEntity(USERS_SET_URL, request, UserResponseDto.class);
    Integer id = null;
    if (response.getBody() != null && response.getBody().getIdUser() != null) {
      id = response.getBody().getIdUser();
    }
    User user = null;
    if (id != null) {
      user = repository.findById(id).orElse(null);
    }
    assertNotNull(user);
    assertEquals(user.getName(), USER_DTO_1.getName().toLowerCase());
    assertEquals(response.getStatusCode(), HttpStatus.OK);

    ResponseEntity<String> response2 =
        restTemplate.exchange(
            USER_GET_BY_ID + user.getId(), HttpMethod.GET, new HttpEntity<>(headers), String.class);
    if (response2.getBody() != null) {
      assertTrue(response2.getBody().contains(user.getName().toLowerCase()));
    }
    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }

  @Test
  void getByIdNotFound() {
    AuthenticationResponse authenticationResponse = getAuthHeadersForManager("admin", "test");

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer_" + authenticationResponse.getJwt());

    ResponseEntity<String> response =
        restTemplate.exchange(
            USER_GET_BY_ID + "-1", HttpMethod.GET, new HttpEntity<>(headers), String.class);

    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
  }

  @Test
  void getByUnauthorized() {
    HttpHeaders headers = new HttpHeaders();

    ResponseEntity<String> response =
        restTemplate.exchange(
            USER_GET_BY_ID, HttpMethod.GET, new HttpEntity<>(headers), String.class);

    assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
  }

  @Test
  void getNotFound() {
    AuthenticationResponse authenticationResponse = getAuthHeadersForManager("admin", "test");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add("Authorization", "Bearer_" + authenticationResponse.getJwt());

    HttpEntity<UserDto> request = new HttpEntity<>(USER_DTO_1, headers);

    ResponseEntity<UserResponseDto> response =
        restTemplate.postForEntity(USERS_SET_URL, request, UserResponseDto.class);
    User user = null;
    if (response.getBody() != null) {
      user = repository.findById(response.getBody().getIdUser()).orElse(null);
    }
    assertNotNull(user);
    assertEquals(user.getName(), USER_DTO_1.getName().toLowerCase());
    assertEquals(response.getStatusCode(), HttpStatus.OK);

    HttpEntity<EmailRequestDto> request2 =
        new HttpEntity<>(EMAIL_REQUEST_DTO_USER_NOT_EXIST, headers);

    ResponseEntity<String> response2 =
        restTemplate.postForEntity(USER_GET_BY_EMAIL, request2, String.class);

    if (response2.getBody() != null) {
      assertTrue(
          response2
              .getBody()
              .contains(
                  "User with email " + EMAIL_REQUEST_DTO_USER_NOT_EXIST.getEmail() + " not found"));
    }
    assertEquals(response2.getStatusCode(), HttpStatus.NOT_FOUND);
  }

  @Test
  void getUnauthorized() {

    HttpHeaders headers = new HttpHeaders();

    HttpEntity<UserDto> request = new HttpEntity<>(USER_DTO_1, headers);

    ResponseEntity<String> response =
        restTemplate.postForEntity(USER_GET_BY_EMAIL, request, String.class);

    assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
  }

  @Test
  void getLast() {
    AuthenticationResponse authenticationResponse = getAuthHeadersForManager("admin", "test");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add("Authorization", "Bearer_" + authenticationResponse.getJwt());

    HttpEntity<UserDto> request = new HttpEntity<>(USER_DTO_1, headers);

    ResponseEntity<UserResponseDto> response =
        restTemplate.postForEntity(USERS_SET_URL, request, UserResponseDto.class);
    User user = null;
    if (response.getBody() != null && response.getBody().getIdUser() != null) {
      user = repository.findById(response.getBody().getIdUser()).orElse(null);
    }
    assertNotNull(user);
    assertEquals(user.getName(), USER_DTO_1.getName().toLowerCase());
    assertEquals(response.getStatusCode(), HttpStatus.OK);

    HttpEntity<UserDto> request2 = new HttpEntity<>(USER_DTO_2, headers);

    ResponseEntity<UserResponseDto> response2 =
        restTemplate.postForEntity(USERS_SET_URL, request2, UserResponseDto.class);
    if (response2.getBody() != null && response2.getBody().getIdUser() != null) {
      user = repository.findById(response2.getBody().getIdUser()).orElse(null);
    }
    assertNotNull(user);
    assertEquals(user.getName(), USER_DTO_2.getName().toLowerCase());
    assertEquals(response.getStatusCode(), HttpStatus.OK);

    ResponseEntity<UserResponseDto> response3 =
        restTemplate.exchange(
            USER_GET_LAST, HttpMethod.GET, new HttpEntity<>(headers), UserResponseDto.class);
    if (response3.getBody() != null && response3.getBody().getIdUser() != null) {
      user = repository.findById(response3.getBody().getIdUser()).orElse(null);
    }
    assertNotNull(user);
    assertEquals(user.getName(), USER_DTO_2.getName().toLowerCase());
    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }

  @Test
  void getLastUnauthorized() {
    HttpHeaders headers = new HttpHeaders();

    ResponseEntity<String> response =
        restTemplate.exchange(
            USER_GET_LAST, HttpMethod.GET, new HttpEntity<>(headers), String.class);

    assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
  }

  @Test
  void getByEmail() {
    AuthenticationResponse authenticationResponse = getAuthHeadersForManager("admin", "test");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add("Authorization", "Bearer_" + authenticationResponse.getJwt());

    HttpEntity<UserDto> request = new HttpEntity<>(USER_DTO_1, headers);

    ResponseEntity<UserResponseDto> response =
        restTemplate.postForEntity(USERS_SET_URL, request, UserResponseDto.class);
    User user = null;
    if (response.getBody() != null && response.getBody().getIdUser() != null) {
      user = repository.findById(response.getBody().getIdUser()).orElse(null);
    }
    assertNotNull(user);
    assertEquals(user.getName(), USER_DTO_1.getName().toLowerCase());
    assertEquals(response.getStatusCode(), HttpStatus.OK);

    HttpEntity<EmailRequestDto> request2 = new HttpEntity<>(EMAIL_REQUEST_DTO_USER_DTO_1, headers);

    ResponseEntity<UserResponseDto> response2 =
        restTemplate.postForEntity(USER_GET_BY_EMAIL, request2, UserResponseDto.class);
    if (response2.getBody() != null && response2.getBody().getIdUser() != null) {
      user = repository.findById(response2.getBody().getIdUser()).orElse(null);
    }
    assertNotNull(user);
    assertEquals(user.getName(), USER_DTO_1.getName().toLowerCase());
    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }

  @Test
  void getByEmailIgnoreCase() {
    AuthenticationResponse authenticationResponse = getAuthHeadersForManager("admin", "test");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add("Authorization", "Bearer_" + authenticationResponse.getJwt());

    HttpEntity<UserDto> request = new HttpEntity<>(USER_DTO_1, headers);

    ResponseEntity<UserResponseDto> response =
        restTemplate.postForEntity(USERS_SET_URL, request, UserResponseDto.class);
    User user = null;
    if (response.getBody() != null && response.getBody().getIdUser() != null) {
      user = repository.findById(response.getBody().getIdUser()).orElse(null);
    }
    assertNotNull(user);
    assertEquals(user.getName(), USER_DTO_1.getName().toLowerCase());
    assertEquals(response.getStatusCode(), HttpStatus.OK);

    HttpEntity<EmailRequestDto> request2 =
        new HttpEntity<>(EMAIL_REQUEST_DTO_USER_UPPERCASE, headers);

    ResponseEntity<UserResponseDto> response2 =
        restTemplate.postForEntity(USER_GET_BY_EMAIL, request2, UserResponseDto.class);
    if (response2.getBody() != null && response2.getBody().getIdUser() != null) {
      user = repository.findById(response2.getBody().getIdUser()).orElse(null);
    }
    assertNotNull(user);
    assertEquals(user.getName(), USER_DTO_1.getName().toLowerCase());
    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }

  @Test
  void getByEmailNotFound() {
    AuthenticationResponse authenticationResponse = getAuthHeadersForManager("admin", "test");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add("Authorization", "Bearer_" + authenticationResponse.getJwt());

    HttpEntity<UserDto> request = new HttpEntity<>(USER_DTO_1, headers);

    ResponseEntity<UserResponseDto> response =
        restTemplate.postForEntity(USERS_SET_URL, request, UserResponseDto.class);
    User user = null;
    if (response.getBody() != null && response.getBody().getIdUser() != null) {
      user = repository.findById(response.getBody().getIdUser()).orElse(null);
    }
    assertNotNull(user);
    assertEquals(user.getName(), USER_DTO_1.getName().toLowerCase());
    assertEquals(response.getStatusCode(), HttpStatus.OK);

    HttpEntity<EmailRequestDto> request2 =
        new HttpEntity<>(EMAIL_REQUEST_DTO_USER_NOT_EXIST, headers);

    ResponseEntity<UserResponseDto> response2 =
        restTemplate.postForEntity(USER_GET_BY_EMAIL, request2, UserResponseDto.class);

    assertEquals(response2.getStatusCode(), HttpStatus.NOT_FOUND);
  }

  @Test
  void getByEmailUnauthorized() {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    HttpEntity<EmailRequestDto> request = new HttpEntity<>(EMAIL_REQUEST_DTO_USER_DTO_1, headers);

    ResponseEntity<String> response =
        restTemplate.postForEntity(USER_GET_BY_EMAIL, request, String.class);
    assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
  }
}
