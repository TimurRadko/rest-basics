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
   * * This method describes a general save (create) operation for all Entities, located in the DB
   *
   * @param t - typed Entities, which transmitted in the method as a args
   * @return Optional<T> - container that is contained T Entity
   */
  Optional<T> save(T t);

  /**
   * This method describes a general getAll (getting a list of all entities) operation for all
   * Entities, located in the DB
   *
   * @param specification - specification used to search for Entities in the database
   * @return List<T> - List of T contained in one of all tables in the DB
   */
  List<T> getEntityList(Specification<T> specification);

  /**
   * * This method describes a general getAll (getting a list of all entities) operation for all
   * Entities with pagination, located in the DB
   *
   * @param specification - specification used to search for Entities in the database
   * @param page - the parameter describes current page
   * @param size - the parameter describes quantity of the Entities for one page
   * @return List<T> - List of T contained in one of all tables in the DB
   */
  List<T> getEntityListWithPagination(Specification<T> specification, Integer page, Integer size);

  /**
   * * This method describes a general get operation for all Entities, located in the DB
   *
   * @param specification - specification used to search for Entities in the database
   * @return Optional<T> - container that is contained Entity (typed parameter)
   */
  Optional<T> getEntity(Specification<T> specification);
}
