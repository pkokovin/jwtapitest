package ru.kokovin.jwtapitest.service;

import ru.kokovin.jwtapitest.model.Manager;

public interface ManagerService {

  Manager register(Manager manager);

  Manager findByEmail(String email);

  Manager findById(int id);

  Manager findByUserName(String username);
}
