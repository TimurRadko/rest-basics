package com.epam.esm.persistence.specification.tag;

import com.epam.esm.persistence.specification.Specification;

public class GetAllTagsSpecification implements Specification {
    private final String sort;

    private static final String QUERY = "SELECT * FROM tags ORDER BY " +
            "CASE WHEN ? ='name-asc' THEN name END ASC, " +
            "CASE WHEN ? ='name-desc' THEN name END DESC, id ASC;";

    public GetAllTagsSpecification(String sort) {
        this.sort = sort;
    }

    @Override
    public String getQuery() {
        return QUERY;
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{sort, sort};
    }
}
