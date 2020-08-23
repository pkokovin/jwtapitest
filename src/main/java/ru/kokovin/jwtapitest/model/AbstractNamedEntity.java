package ru.kokovin.jwtapitest.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractNamedEntity extends AbstractBaseEntity {

  @NotBlank
  @Size(min = 2, max = 100)
  @Column(name = "username", nullable = false)
  protected String name;

  @Override
  public String toString() {
    return super.toString() + '(' + name + ')';
  }
}
