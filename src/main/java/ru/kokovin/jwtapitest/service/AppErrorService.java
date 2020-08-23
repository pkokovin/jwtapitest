package ru.kokovin.jwtapitest.service;

import ru.kokovin.jwtapitest.model.AppError;

public interface AppErrorService {

  AppError save(AppError appError);

  AppError getLast();
}
