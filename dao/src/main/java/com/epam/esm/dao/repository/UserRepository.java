package com.epam.esm.dao.repository;

import com.epam.esm.dao.entity.User;

import java.util.List;
import java.util.Optional;

/** * This interface describes a common operations with User's Entities situated in the DB */
public interface UserRepository {
  List<User> getAllUsers();

  Optional<User> getUserById(long id);
}
