package ru.kokovin.jwtapitest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kokovin.jwtapitest.util.DateTimeUtil;

@Entity
@Table(name = "errors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppError extends AbstractBaseEntity {
  @Column(name = "message")
  private String message;

  @Override
  public String toString() {
    return "AppError{"
        + "message='"
        + message
        + '\''
        + "created='"
        + DateTimeUtil.dateToString(getCreated())
        + '\''
        + '}';
  }
}
