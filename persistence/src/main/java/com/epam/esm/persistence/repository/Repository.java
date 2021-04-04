package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.Entity;
import com.epam.esm.persistence.specification.Specification;

import java.util.List;
import java.util.Optional;

/***
 * This interface describes a common abstraction for work with Entities situated in the DB
 * @param <T> - the interface is typed by T extends Entity
 */
public interface Repository<T extends Entity> {
    /***
     * This method describes a general save (create) operation for all Entities, located in the DB
     *
     * @param t - Entity (typed parameter), which transmitted in the method as a args
     * @return Optional<T> - container that is contained Entity (a typed parameter)
     */
    Optional<T> save(T t);

    /**
     * This method describes a general getAll (getting a list of all entities) operation for all Entities,
     * located in the DB
     *
     * @param specification - specification used to search for Entities in the database
     * @return List<T> - List of T contained in one of all tables in the DB
     */
    List<T> getEntitiesListBySpecification(Specification specification);

    /***
     * This method describes a general get (getting a entity by parameter) operation for all Entities,
     * located in the DB
     *
     * @param specification - specification used to search for Entities in the database
     * @return Optional<T> - container that is contained Entity (typed parameter)
     */
    Optional<T> getEntityBySpecification(Specification specification);
}
