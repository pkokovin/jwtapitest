package ru.kokovin.jwtapitest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kokovin.jwtapitest.model.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {

  Manager findByName(String username);

  Manager findByEmail(String email);

  Manager findById(int id);
}
