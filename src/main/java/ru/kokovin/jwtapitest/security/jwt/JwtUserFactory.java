package ru.kokovin.jwtapitest.security.jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.kokovin.jwtapitest.model.Manager;
import ru.kokovin.jwtapitest.model.Role;

public final class JwtUserFactory {
  public JwtUserFactory() {}

  /** Creates JwtUser from Manager. */
  public static JwtUser create(Manager manager) {
    return new JwtUser(
        manager.getId(),
        manager.getName(),
        manager.getPassword(),
        manager.getEmail(),
        true,
        manager.getUpdated(),
        mapToGrantedAuthorities(new ArrayList<>(manager.getRoles())));
  }

  private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> managerRoles) {
    return managerRoles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());
  }
}
