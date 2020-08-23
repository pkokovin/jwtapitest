package ru.kokovin.jwtapitest.service;

import java.util.List;
import ru.kokovin.jwtapitest.model.User;

public interface UserService {

  User create(User user);

  List<User> getAll();

  User findByEmail(String email);

  User findById(int id);

  void delete(int id);

  User findLastCreated();
}
