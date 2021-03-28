package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.Entity;
import com.epam.esm.persistence.specification.Specification;

import java.util.List;

//TODO: write javadoc for this interface
public interface Repository<T extends Entity> {

    //TODO: Think about Implementation using Optional
    T create(T t);

    //read statements
    List<T> getListBySpecification(Specification specification);
    T getEntityBySpecification(Specification specification);

    void delete(T t);
}
