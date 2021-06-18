package com.epam.esm.service;

import java.util.Optional;

/**
 * * The interface describes a common logic with work with all Entities getting from persistence
 * layer
 *
 * @param <T> - the interface is typed by T
 */
public interface Service<T> {
  /**
   * * This method describes a general get (getting a entity by parameter) operation for all
   * Entities, from persistence layer
   *
   * @param id - passed into the method id Entity's parameter that required for work with the DB
   * @return Optional<T> - container that is contained Entity (a typed parameter)
   */
  Optional<T> getById(long id);
}
