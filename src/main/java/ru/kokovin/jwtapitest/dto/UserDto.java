package ru.kokovin.jwtapitest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import ru.kokovin.jwtapitest.model.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto extends BaseDto {

  @NotBlank
  @Size(min = 2, max = 100)
  private String name;

  @Email
  @NotBlank
  @Size(max = 100)
  private String email;

  @Range(min = 1, max = 100)
  @NotNull
  private Integer age;

  @NotNull private Date created = new Date();

  /** Constructor without created. */
  public UserDto(String name, String email, Integer age) {
    this.name = name;
    this.email = email;
    this.age = age;
    created = new Date();
  }

  /** Convert User to UserDto. */
  public User fromDto() {
    User user = new User();
    user.setId(id);
    user.setName(name.toLowerCase());
    user.setEmail(email.toLowerCase());
    user.setCreated(created);
    user.setAge(age);
    return user;
  }

  /** Convert UserDto to User. */
  public static UserDto fromUser(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setName(user.getName());
    userDto.setEmail(user.getEmail());
    userDto.setAge(user.getAge());
    userDto.setCreated(user.getCreated());

    return userDto;
  }

  @Override
  public String toString() {
    return "UserDto{"
        + "name='"
        + name
        + '\''
        + ", email='"
        + email
        + '\''
        + ", age="
        + age
        + ", created="
        + created
        + ", id="
        + id
        + '}';
  }
}
