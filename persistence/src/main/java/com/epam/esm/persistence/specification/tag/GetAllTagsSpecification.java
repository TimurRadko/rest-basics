package com.epam.esm.persistence.specification.tag;

import com.epam.esm.persistence.specification.Specification;

public class GetAllTagsSpecification implements Specification {
    private static final String QUERY = "SELECT * FROM tags";

    @Override
    public String getQuery() {
        return QUERY;
    }

    @Override
    public Object[] getArgs() {
        return new Object[0];
    }
}
