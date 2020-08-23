package ru.kokovin.jwtapitest.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kokovin.jwtapitest.model.Manager;
import ru.kokovin.jwtapitest.security.jwt.JwtUser;
import ru.kokovin.jwtapitest.security.jwt.JwtUserFactory;
import ru.kokovin.jwtapitest.service.ManagerService;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

  private final ManagerService managerService;

  @Autowired
  public JwtUserDetailsService(ManagerService managerService) {
    this.managerService = managerService;
  }

  @Override
  public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
    Manager manager = managerService.findByUserName(name);

    if (manager == null) {
      throw new UsernameNotFoundException("Manager with username " + name + " not found");
    }

    JwtUser jwtUser = JwtUserFactory.create(manager);
    log.info("Manager with username {} successfully loaded", name);
    return jwtUser;
  }
}
