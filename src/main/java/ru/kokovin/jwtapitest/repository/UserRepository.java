package ru.kokovin.jwtapitest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kokovin.jwtapitest.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

  User findByEmail(String email);

  User findById(int id);

  User findFirstByOrderByCreatedDesc();
}
