package com.epam.esm.persistence.specification.gift;

import com.epam.esm.persistence.specification.Specification;

public class GetGiftCertificatesByTagNameSpecification implements Specification {
    private final String name;

    private static final String QUERY = "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration, " +
            "gc.create_date, gc.last_update_date " +
            "FROM gift_certificates gc " +
            "INNER JOIN gift_certificates_tags gct ON gc.id = gct.gift_certificate_id " +
            "INNER JOIN tags t ON t.id = gct.tag_id WHERE t.name=?;";

    public GetGiftCertificatesByTagNameSpecification(String name) {
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
