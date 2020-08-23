package ru.kokovin.jwtapitest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import ru.kokovin.jwtapitest.model.AppError;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppErrorDto {

  @NotBlank private String msg;
  @NotNull private Date created;

  /** Produce AppError from AppErrorDto. */
  public AppError fromDto() {
    AppError appError = new AppError();
    appError.setCreated(created);
    appError.setMessage(msg);
    return appError;
  }

  /** Produce AppErrorDto from AppError. */
  public static AppErrorDto fromAppError(AppError appError) {
    AppErrorDto appErrorDto = new AppErrorDto();
    appErrorDto.setCreated(appError.getCreated());
    appErrorDto.setMsg(appError.getMessage());
    return appErrorDto;
  }
}
