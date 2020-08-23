package ru.kokovin.jwtapitest.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kokovin.jwtapitest.dto.ManagerDto;
import ru.kokovin.jwtapitest.model.Manager;
import ru.kokovin.jwtapitest.service.ManagerService;
import ru.kokovin.jwtapitest.util.exceptions.ResourceNotFoundException;

/** Add or delete managers not implemented yet due task. */
@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestControllerV1 {

  @Autowired private ManagerService service;

  /** Returns manager by id. */
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping(value = "managers/{id}")
  public ResponseEntity<ManagerDto> getManagerById(@PathVariable(name = "id") Integer id) {
    Manager manager = service.findById(id);

    if (manager == null) {
      throw new ResourceNotFoundException("There is no manager with id " + id);
    }

    ManagerDto result = ManagerDto.fromManager(manager);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
