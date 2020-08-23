package ru.kokovin.jwtapitest.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kokovin.jwtapitest.dto.AppErrorDto;
import ru.kokovin.jwtapitest.model.AppError;
import ru.kokovin.jwtapitest.service.AppErrorService;
import ru.kokovin.jwtapitest.util.exceptions.ResourceNotFoundException;

@RestController
@Slf4j
@RequestMapping(value = "/error/last")
public class AppErrorRestControllerV1 {

  @Autowired private AppErrorService service;

  /** Returns last error from DB. */
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @GetMapping
  public ResponseEntity<AppErrorDto> getLatestError() {
    AppError appError = service.getLast();
    if (appError == null) {
      throw new ResourceNotFoundException("No errors found");
    }
    AppErrorDto appErrorDto = AppErrorDto.fromAppError(appError);
    return ResponseEntity.ok(appErrorDto);
  }
}
