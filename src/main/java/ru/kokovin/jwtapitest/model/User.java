package ru.kokovin.jwtapitest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractNamedEntity {

  @Column(name = "email")
  private String email;

  @Column(name = "age")
  private int age;

  @Override
  public String toString() {
    return "User{" + "name='" + name + '\'' + ", age=" + age + ", email='" + email + '\'' + '}';
  }
}
