package com.epam.esm.persistence.specification.gifttag;

import com.epam.esm.persistence.specification.Specification;

public class GetAllTagsByGiftCertificateIdSpecification implements Specification {
    private final Long id;
    private static final String QUERY = "SELECT * FROM gift_certificates_tags WHERE gift_certificate_id=?;";

    public GetAllTagsByGiftCertificateIdSpecification(Long id) {
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
