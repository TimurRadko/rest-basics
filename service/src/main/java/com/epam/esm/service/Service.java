package com.epam.esm.service;

import java.util.List;
import java.util.Optional;

//TODO: write javadoc for this interface
public interface Service<T> {

    Optional<T> save(T t);

    //read statements
    List<T> getAll();
    Optional<T> getById(long id);

    Optional<T> update(T t);

    void delete(long id);
}
