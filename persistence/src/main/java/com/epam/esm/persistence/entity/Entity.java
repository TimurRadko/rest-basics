package com.epam.esm.persistence.entity;

import java.io.Serializable;

/**
 * A marker interface for limiting the scope of processed entities in DAO layers
 */
public interface Entity extends Serializable, Cloneable {
    /**
     * This method allow to get entities ID in DB.
     *
     * @return entities id
     */
    Long getId();

    /**
     * This method allow to set entities ID in DB.
     */
    void setId(Long id);
}
