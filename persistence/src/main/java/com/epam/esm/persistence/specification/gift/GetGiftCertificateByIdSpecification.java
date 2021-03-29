package com.epam.esm.persistence.specification.gift;

import com.epam.esm.persistence.specification.Specification;

public class GetGiftCertificateByIdSpecification implements Specification {
    private static final String QUERY = "SELECT * FROM gift_certificates WHERE id=?";
    private final long id;

    public GetGiftCertificateByIdSpecification(long id) {
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
