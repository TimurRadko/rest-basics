package com.epam.esm.persistence.specification.gifttag;

import com.epam.esm.persistence.specification.Specification;

public class GetGiftCertificateTagByGiftIdSpecification implements Specification {
    private final long id;
    private static final String QUERY = "SELECT * FROM gift_certificates_tags WHERE gift_certificate_id=?;";

    public GetGiftCertificateTagByGiftIdSpecification(long id) {
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
