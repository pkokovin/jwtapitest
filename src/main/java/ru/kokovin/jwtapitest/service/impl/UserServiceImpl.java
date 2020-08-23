package ru.kokovin.jwtapitest.service.impl;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kokovin.jwtapitest.model.User;
import ru.kokovin.jwtapitest.repository.UserRepository;
import ru.kokovin.jwtapitest.service.UserService;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository repository;

  @Autowired
  public UserServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public User create(User user) {
    String email = user.getEmail().toLowerCase();
    user.setEmail(email);
    User createdUser = repository.save(user);
    log.info("User {} succsessfully created", createdUser);
    return createdUser;
  }

  @Override
  public List<User> getAll() {
    List<User> result = repository.findAll();
    log.info("{} users found", result.size());
    return result;
  }

  @Override
  public User findByEmail(String email) {
    String loEmail = email.toLowerCase();
    User result = repository.findByEmail(loEmail);
    log.info("User {} found by email {}", result, loEmail);
    return result;
  }

  @Override
  public User findById(int id) {
    User result = repository.findById(id);
    if (result == null) {
      log.info("User with id {} not found", id);
      return null;
    }
    log.info("User {} found by id {}", result, id);
    return result;
  }

  @Override
  public void delete(int id) {
    repository.deleteById(id);
    log.info("User with id {} deleted", id);
  }

  @Override
  public User findLastCreated() {
    User lastCreated = repository.findFirstByOrderByCreatedDesc();
    log.info("Last created user: {} ", lastCreated);
    return lastCreated;
  }
}
