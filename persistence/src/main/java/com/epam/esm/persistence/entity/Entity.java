package com.epam.esm.persistence.entity;

import java.io.Serializable;

/**
 * A marker interface for limiting the scope of processed entities in DAO layers
 */
public interface Entity extends Serializable, Cloneable {
    /**
     * This interface describes all Entities in DB. It is used to eliminate
     * the possibility of inserting entities that don't implement it into the DB.
     *
     * @return entities id
     */
    Long getId();

    /**
     *
     */
    void setId(Long id);
}
