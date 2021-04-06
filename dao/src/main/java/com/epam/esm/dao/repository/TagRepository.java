package com.epam.esm.dao.repository;

import com.epam.esm.dao.entity.Tag;

/** * This interface describes a common operations with Tag's Entities situated in the DB */
public interface TagRepository extends Repository<Tag> {
  /**
   * * Delete Tag with the specified id
   *
   * @param id id - passed into the method id parameter that is contained in one of all tables in
   *     the DB
   * @return int - return value more than 0, when Tag was deleted
   */
  int delete(long id);
}
