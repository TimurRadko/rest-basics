package com.epam.esm.persistence.specification;

public interface Specification {
    String getQuery();

    //TODO:Think about this method
    Object[] getArgs();
}
