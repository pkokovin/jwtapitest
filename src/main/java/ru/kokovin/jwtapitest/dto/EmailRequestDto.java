package ru.kokovin.jwtapitest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class EmailRequestDto {
  private String email;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public EmailRequestDto(String email) {
    this.email = email;
  }
}
