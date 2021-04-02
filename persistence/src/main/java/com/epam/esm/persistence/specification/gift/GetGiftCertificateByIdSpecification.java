package com.epam.esm.persistence.specification.gift;

import com.epam.esm.persistence.specification.Specification;

public class GetGiftCertificateByIdSpecification implements Specification {
    private final long id;

    private static final String QUERY = "SELECT id, name, description, price, " +
            "duration, create_date, last_update_date FROM gift_certificates WHERE id=?;";

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
