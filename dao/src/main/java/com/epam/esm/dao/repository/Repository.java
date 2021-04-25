package com.epam.esm.dao.repository;

import com.epam.esm.dao.entity.TableEntity;
import com.epam.esm.dao.specification.Specification;

import java.util.List;
import java.util.Optional;

/**
 * * This interface describes a common abstraction for work with Entities situated in the DB
 *
 * @param <T> - the interface is typed by T extends Entity
 */
public interface Repository<T extends TableEntity> {

  /**
   * This method describes a general getAll (getting a list of all entities) operation for all
   * Entities, located in the DB
   *
   * @param specification - specification used to search for Entities in the database
   * @return List<T> - List of T contained in one of all tables in the DB
   */
  List<T> getEntityListBySpecification(Specification<T> specification);

  List<T> getEntityListWithPaginationBySpecification(
      Specification<T> specification, int page, int size);

  /**
   * * This method describes a general get (getting a entity by parameter) operation for all
   * Entities, located in the DB
   *
   * @param specification - specification used to search for Entities in the database
   * @return Optional<T> - container that is contained Entity (typed parameter)
   */
  Optional<T> getEntityBySpecification(Specification<T> specification);
}
