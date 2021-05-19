package com.epam.esm.dao.repository;

import com.epam.esm.dao.entity.Users;

import java.util.Optional;

/** * This interface describes a common operations with User's Entities situated in the DB */
public interface UserRepository extends Repository<Users> {
  /**
   * * Updating the Gift Certificate with the parameters passed in the Entity
   *
   * @param users - passed into the method User that is contained in one of all tables in the DB
   * @return Optional<User> - container that is contained User
   */
  Optional<Users> update(Users users);
}
