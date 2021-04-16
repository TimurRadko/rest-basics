package com.epam.esm.web.controller;

import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/users")
public class UsersController {
  private UserService userService;

  @Autowired
  public UsersController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping()
  public List<UserDto> getAll() {
    return userService.getAll();
  }

  @GetMapping("/{id}")
  public UserDto getById(@PathVariable Long id) {
    return userService
        .getById(id)
        .orElseThrow(
            () -> new EntityNotFoundException("Requested resource not found (id = " + id + ")"));
  }
}
