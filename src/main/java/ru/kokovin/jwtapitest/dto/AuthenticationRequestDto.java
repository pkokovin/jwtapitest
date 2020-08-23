package ru.kokovin.jwtapitest.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
  private String username;
  private String password;
}
