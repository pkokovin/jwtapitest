package ru.kokovin.jwtapitest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kokovin.jwtapitest.model.Manager;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ManagerDto extends BaseDto {

  @NotBlank
  @Size(min = 2, max = 100)
  private String name;

  @Email
  @NotBlank
  @Size(max = 100)
  private String email;

  /** Convert ManagerDto to Manager. */
  public Manager toManger() {
    Manager manager = new Manager();
    manager.setId(id);
    manager.setName(name.toLowerCase());
    manager.setEmail(email.toLowerCase());
    return manager;
  }

  /** Convert Manager to ManagerDto. */
  public static ManagerDto fromManager(Manager manager) {
    ManagerDto managerDto = new ManagerDto();
    managerDto.setId(manager.getId());
    managerDto.setName(manager.getName());
    managerDto.setEmail(manager.getEmail());

    return managerDto;
  }

  @Override
  public String toString() {
    return "ManagerDto{" + "name='" + name + '\'' + ", email='" + email + '\'' + ", id=" + id + '}';
  }
}
