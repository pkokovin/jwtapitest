package ru.kokovin.jwtapitest.rest;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kokovin.jwtapitest.dto.EmailRequestDto;
import ru.kokovin.jwtapitest.dto.UserDto;
import ru.kokovin.jwtapitest.dto.UserResponseDto;
import ru.kokovin.jwtapitest.model.User;
import ru.kokovin.jwtapitest.service.UserService;
import ru.kokovin.jwtapitest.util.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(value = "/profiles")
public class ProfilesRestControllerV1 {
  @Autowired UserService service;

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @GetMapping
  @ResponseStatus(value = HttpStatus.OK)
  public List<UserDto> getAll() {
    return service.getAll().stream().map(UserDto::fromUser).collect(Collectors.toList());
  }

  /** Get user controller returns from @Params name, email, age. */
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @PostMapping(value = "/set")
  public ResponseEntity<UserResponseDto> set(@Valid @RequestBody UserDto userDto) {
    User user = userDto.fromDto();
    user.setUpdated(new Date());
    User created = service.create(user);
    UserResponseDto userResponseDto = UserResponseDto.fromUser(created);
    return ResponseEntity.ok(userResponseDto);
  }

  /** Returns user by id. */
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @GetMapping(value = "/{id}")
  public ResponseEntity<UserDto> get(@PathVariable(name = "id") int id) {
    User user = service.findById(id);
    if (user == null) {
      throw new ResourceNotFoundException("User with id " + id + " not found");
    }
    UserDto userDto = UserDto.fromUser(user);
    return ResponseEntity.ok(userDto);
  }

  /** Returns last registered user. */
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @GetMapping(value = "/last")
  public ResponseEntity<UserDto> getLast() {
    User user = service.findLastCreated();
    if (user == null) {
      throw new ResourceNotFoundException("Users not found");
    }
    UserDto userDto = UserDto.fromUser(user);
    return ResponseEntity.ok(userDto);
  }

  /** Returns user by email. */
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @PostMapping(value = "/get")
  public ResponseEntity<UserDto> getByEmail(@RequestBody EmailRequestDto email) {
    User user = service.findByEmail(email.getEmail().toLowerCase());
    if (user == null) {
      throw new ResourceNotFoundException(
          "User with email " + email.getEmail().toLowerCase() + " not found");
    }
    UserDto userDto = UserDto.fromUser(user);
    return ResponseEntity.ok(userDto);
  }
}
