package com.epam.esm.dao.entity;

/** A marker interface for limiting the scope of processed entities in dao layers */
public interface TableEntity {
  /**
   * This method allows to get Entity's id, that have all Entities in the DB.
   *
   * @return entities id
   */
  Long getId();
  /** This method allows to set Entity's id, that have all Entities in the DB. */
  void setId(Long id);
}
