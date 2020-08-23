package ru.kokovin.jwtapitest.service.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kokovin.jwtapitest.model.Manager;
import ru.kokovin.jwtapitest.model.Role;
import ru.kokovin.jwtapitest.repository.ManagerRepository;
import ru.kokovin.jwtapitest.repository.RoleRepository;
import ru.kokovin.jwtapitest.service.ManagerService;

@Service
@Slf4j
public class ManagerServiceImpl implements ManagerService {

  private final ManagerRepository managerRepository;
  private final RoleRepository roleRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  /** Constructor to autowire fields. */
  @Autowired
  public ManagerServiceImpl(
      ManagerRepository managerRepository,
      RoleRepository roleRepository,
      BCryptPasswordEncoder passwordEncoder) {
    this.managerRepository = managerRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Manager register(Manager manager) {
    Role roleManager = roleRepository.findByName("ROLE_USER");
    List<Role> managerRoles = new ArrayList<>();
    managerRoles.add(roleManager);
    manager.setPassword(passwordEncoder.encode(manager.getPassword()));
    manager.setRoles(managerRoles);
    Manager registredManager = managerRepository.save(manager);

    log.info("registered new manager successfully: {}", registredManager);
    return registredManager;
  }

  @Override
  public Manager findByEmail(String email) {
    String loEmail = email.toLowerCase();
    Manager result = managerRepository.findByEmail(loEmail);
    log.info("manager {} found by email {}", result, loEmail);
    return result;
  }

  @Override
  public Manager findById(int id) {
    Manager result = managerRepository.findById(id);
    if (result == null) {
      log.info("manager with id {} not found", id);
      return null;
    }
    log.info("manager {} found by id {}", result, id);
    return result;
  }

  @Override
  public Manager findByUserName(String username) {
    Manager result = managerRepository.findByName(username);
    log.info("manager {} found by username {}", result, username);
    return result;
  }
}
