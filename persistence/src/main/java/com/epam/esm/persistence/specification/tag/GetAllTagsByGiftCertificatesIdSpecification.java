package com.epam.esm.persistence.specification.tag;

import com.epam.esm.persistence.specification.Specification;

public class GetAllTagsByGiftCertificatesIdSpecification implements Specification {
    private final long id;

    private static final String QUERY =
            "SELECT t.id, t.name FROM tags t " +
                    "INNER JOIN gift_certificates_tags gct " +
                    "ON t.id=gct.tag_id WHERE gct.gift_certificate_id=?;";

    public GetAllTagsByGiftCertificatesIdSpecification(long id) {
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
