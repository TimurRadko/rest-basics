package com.epam.esm.service;

import java.util.List;
import java.util.Optional;

//TODO: write javadoc for this interface
public interface Service<T> {
    Optional<T> save(T t);

    List<T> getAll(String sort);

    Optional<T> getById(long id);

    void delete(long id);
}
