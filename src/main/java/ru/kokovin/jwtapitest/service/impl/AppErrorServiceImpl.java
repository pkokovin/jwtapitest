package ru.kokovin.jwtapitest.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kokovin.jwtapitest.model.AppError;
import ru.kokovin.jwtapitest.repository.AppErrorRepository;
import ru.kokovin.jwtapitest.service.AppErrorService;

@Service
@Slf4j
public class AppErrorServiceImpl implements AppErrorService {

  private final AppErrorRepository repository;

  @Autowired
  public AppErrorServiceImpl(AppErrorRepository repository) {
    this.repository = repository;
  }

  @Override
  public AppError save(AppError appError) {
    return repository.save(appError);
  }

  @Override
  public AppError getLast() {
    return repository.findFirstByOrderByCreatedDesc();
  }
}
