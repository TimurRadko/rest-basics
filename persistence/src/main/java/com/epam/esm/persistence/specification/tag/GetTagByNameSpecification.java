package com.epam.esm.persistence.specification.tag;

import com.epam.esm.persistence.specification.Specification;

public class GetTagByNameSpecification implements Specification {
    private final String name;
    private static final String QUERY = "SELECT id, name FROM tags WHERE name = ?;";

    public GetTagByNameSpecification(String name) {
        this.name = name;
    }

    @Override
    public String getQuery() {
        return QUERY;
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{name};
    }
}
