package com.epam.esm.service;

import java.util.List;

//TODO: write javadoc for this interface
public interface Service<T> {

    T create(T t);

    //read statements
    List<T> getAll();
    T getById(long id);

    T update(T t);

    void delete(long id);
}
