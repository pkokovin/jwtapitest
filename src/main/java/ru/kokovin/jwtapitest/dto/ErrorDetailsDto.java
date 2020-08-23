package ru.kokovin.jwtapitest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.kokovin.jwtapitest.util.exceptions.ErrorDetails;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDetailsDto {
  private String msg;

  /** Converts ErrorDetails to ErrorDetailsDto. */
  public static ErrorDetailsDto fromErrorDetails(ErrorDetails errorDetails) {
    ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto();
    errorDetailsDto.setMsg(errorDetails.getMessage());
    return errorDetailsDto;
  }
}
