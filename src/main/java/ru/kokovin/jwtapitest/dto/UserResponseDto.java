package ru.kokovin.jwtapitest.dto;

import lombok.Data;
import ru.kokovin.jwtapitest.model.User;

@Data
public class UserResponseDto {
  Integer idUser;

  /** Convert User to UserResponseDto (idUser). */
  public static UserResponseDto fromUser(User user) {
    UserResponseDto responseDto = new UserResponseDto();
    responseDto.setIdUser(user.getId());
    return responseDto;
  }
}
