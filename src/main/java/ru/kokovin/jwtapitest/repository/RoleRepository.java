package ru.kokovin.jwtapitest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kokovin.jwtapitest.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

  Role findByName(String name);
}
