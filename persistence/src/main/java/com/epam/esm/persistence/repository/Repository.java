package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.Entity;
import com.epam.esm.persistence.specification.Specification;

import java.util.List;
import java.util.Optional;

//TODO: write javadoc for this interface
public interface Repository<T extends Entity> {
    Optional<T> save(T t);
    List<T> getListBySpecification(Specification specification);
    Optional<T> getEntityBySpecification(Specification specification);
    void delete(T t);
}
