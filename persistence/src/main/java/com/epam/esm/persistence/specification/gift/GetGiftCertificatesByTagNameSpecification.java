package com.epam.esm.persistence.specification.gift;

import com.epam.esm.persistence.specification.Specification;

public class GetGiftCertificatesByTagNameSpecification implements Specification {
    private static final String QUERY = "";

    @Override
    public String getQuery() {
        return QUERY;
    }

    @Override
    public Object[] getArgs() {
        return new Object[0];
    }
}
