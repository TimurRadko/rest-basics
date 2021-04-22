package com.epam.esm.dao.repository;

import com.epam.esm.dao.entity.User;

import java.util.Optional;

/** * This interface describes a common operations with User's Entities situated in the DB */
public interface UserRepository extends Repository<User> {
  /**
   * * Updating the Gift Certificate with the parameters passed in the Entity
   *
   * @param user - passed into the method User that is contained in one of all tables in the DB
   * @return Optional<User> - container that is contained User
   */
  Optional<User> update(User user);
}
