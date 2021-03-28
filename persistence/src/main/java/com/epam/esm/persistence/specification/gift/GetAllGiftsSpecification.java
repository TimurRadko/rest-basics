package com.epam.esm.persistence.specification.gift;

import com.epam.esm.persistence.specification.Specification;

public class GetAllGiftsSpecification implements Specification {
    private static final String QUERY = "SELECT * FROM gift_certificates";

    @Override
    public String getQuery() {
        return QUERY;
    }

    @Override
    public Object[] getArgs() {
        return new Object[0];
    }
}
