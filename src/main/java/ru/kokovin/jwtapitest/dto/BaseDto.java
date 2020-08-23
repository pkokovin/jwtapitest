package ru.kokovin.jwtapitest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.kokovin.jwtapitest.HasId;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseDto implements HasId {
  protected Integer id;
}
