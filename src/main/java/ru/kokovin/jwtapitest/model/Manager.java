package ru.kokovin.jwtapitest.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "managers",
    uniqueConstraints = {
      @UniqueConstraint(columnNames = "email", name = "managers_unique_email_idx")
    })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Manager extends AbstractNamedEntity {

  @Column(name = "email", nullable = false, unique = true)
  @Email
  @NotBlank
  @Size(max = 100)
  private String email;

  @Column(name = "password", nullable = false)
  @NotBlank
  @Size(min = 5, max = 100)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "manager_roles",
      joinColumns = {@JoinColumn(name = "manager_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
  @Column()
  private List<Role> roles;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;

  @Override
  public String toString() {
    return "Manager{" + "username='" + name + '\'' + ", email='" + email + '\'' + '}';
  }
}
