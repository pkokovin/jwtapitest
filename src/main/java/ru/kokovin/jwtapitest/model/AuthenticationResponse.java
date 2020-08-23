package ru.kokovin.jwtapitest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter

/** Incorrect behaviour with maven checkstyle plugin. */
// @RequiredArgsConstructor(onConstructor=@__(@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)))
public class AuthenticationResponse {
  private final String jwt;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public AuthenticationResponse(String jwt) {
    this.jwt = jwt;
  }
}
