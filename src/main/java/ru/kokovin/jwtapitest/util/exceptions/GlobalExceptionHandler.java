package ru.kokovin.jwtapitest.util.exceptions;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.kokovin.jwtapitest.dto.ErrorDetailsDto;
import ru.kokovin.jwtapitest.model.AppError;
import ru.kokovin.jwtapitest.service.AppErrorService;
import ru.kokovin.jwtapitest.util.ValidationUtil;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @Autowired private AppErrorService service;

  /** Handle ResourceNotFoundException. */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> resourceNotFoundException(
      ResourceNotFoundException ex, WebRequest request) {
    ErrorDetails errorDetails =
        new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
    saveError(errorDetails);
    return new ResponseEntity<>(
        ErrorDetailsDto.fromErrorDetails(errorDetails), HttpStatus.NOT_FOUND);
  }

  /** Handle DataIntegrityViolationException. */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<?> emailAlreadyRegistredException(
      DataIntegrityViolationException ex, WebRequest request) {
    String rootMsg = ValidationUtil.getRootCause(ex).getMessage();
    ErrorDetails errorDetails =
        new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
    ;
    if (rootMsg != null) {
      String lowerCaseMsg = rootMsg.toLowerCase();
      if (lowerCaseMsg.contains("users_email_key")) {
        errorDetails =
            new ErrorDetails(new Date(), "Such email alredy exists", request.getDescription(false));
        saveError(errorDetails);
        return new ResponseEntity<>(
            ErrorDetailsDto.fromErrorDetails(errorDetails), HttpStatus.FORBIDDEN);
      }
    }
    return new ResponseEntity<>(
        ErrorDetailsDto.fromErrorDetails(errorDetails), HttpStatus.CONFLICT);
  }

  /** Handle MethodArgumentNotValidException. */
  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<?> validationExceptionHandler(
      MethodArgumentNotValidException ex, WebRequest request) {
    String rootMsg = ValidationUtil.getRootCause(ex).getMessage();
    ErrorDetails errorDetails =
        new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
    if (rootMsg != null) {
      String lowerCaseMsg = rootMsg.toLowerCase();
      if (lowerCaseMsg.contains("email")) {
        errorDetails =
            new ErrorDetails(new Date(), "Wrong email format", request.getDescription(false));
        saveError(errorDetails);
        return new ResponseEntity<>(
            ErrorDetailsDto.fromErrorDetails(errorDetails), HttpStatus.BAD_REQUEST);
      }
    }
    return new ResponseEntity<>(
        ErrorDetailsDto.fromErrorDetails(errorDetails), HttpStatus.BAD_REQUEST);
  }

  /** Handle other Exceptions. */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
    ErrorDetails errorDetails =
        new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
    saveError(errorDetails);
    return new ResponseEntity<>(
        ErrorDetailsDto.fromErrorDetails(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /** Save errors to database. */
  private void saveError(ErrorDetails errorDetails) {
    AppError appError = new AppError();
    appError.setCreated(errorDetails.getTimestamp());
    appError.setMessage(errorDetails.getMessage());
    appError.setUpdated(new Date());
    AppError saved = service.save(appError);
    log.info("Error {} saved", saved);
  }
}
