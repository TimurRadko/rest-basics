package com.epam.esm.persistence.specification;

public interface Specification {
    String getQuery();

    Object[] getArgs();
}
