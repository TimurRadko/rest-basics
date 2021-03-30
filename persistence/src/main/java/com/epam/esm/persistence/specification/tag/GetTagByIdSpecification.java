package com.epam.esm.persistence.specification.tag;

import com.epam.esm.persistence.specification.Specification;

public class GetTagByIdSpecification implements Specification {
    private final long id;
    private static final String QUERY = "SELECT * FROM gift_certificates WHERE id=?;";

    public GetTagByIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public String getQuery() {
        return QUERY;
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{id};
    }
}
