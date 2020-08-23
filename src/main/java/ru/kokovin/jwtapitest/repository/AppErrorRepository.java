package ru.kokovin.jwtapitest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kokovin.jwtapitest.model.AppError;

public interface AppErrorRepository extends JpaRepository<AppError, Integer> {
  //    return last created error record
  AppError findFirstByOrderByCreatedDesc();
}
