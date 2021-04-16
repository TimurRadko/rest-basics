package com.epam.esm.service.impl;

import com.epam.esm.dao.repository.UserRepository;
import com.epam.esm.dao.specification.user.GetAllUsersSpecification;
import com.epam.esm.dao.specification.user.GetUserByIdSpecification;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
  private UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<UserDto> getAll() {
    return userRepository.getEntityListBySpecification(new GetAllUsersSpecification()).stream()
        .map((UserDto::new))
        .collect(Collectors.toList());
  }

  @Override
  public Optional<UserDto> getById(long id) {
    return userRepository
        .getEntityBySpecification(new GetUserByIdSpecification(id))
        .map(UserDto::new);
  }

  @Override
  public Optional<UserDto> save(UserDto userDto) {
    return Optional.empty();
  }

  @Override
  public int delete(long id) {
    return 0;
  }
}
