package com.epam.esm.dao.repository;

import com.epam.esm.dao.entity.Tag;

import java.util.Optional;

/** * This interface describes a common operations with Tag's Entities situated in the DB */
public interface TagRepository extends Repository<Tag> {
  /**
   * * This method describes a general save (create) operation for all Tags, located in the DB
   *
   * @param tag - Tag, which transmitted in the method as a args
   * @return Optional<Tag> - container that is contained Tag
   */
  Optional<Tag> save(Tag tag);
  /**
   * * This method describes a general delete operation for all Tags with the specified id, located
   * in the DB
   *
   * @param id id - passed into the method id parameter that is contained in one of all tables in
   *     the DB
   * @return int - return value more than 0, when Tag was deleted
   */
  int delete(long id);
}
