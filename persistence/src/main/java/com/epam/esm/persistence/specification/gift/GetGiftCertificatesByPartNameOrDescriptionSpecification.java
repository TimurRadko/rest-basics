package com.epam.esm.persistence.specification.gift;

import com.epam.esm.persistence.specification.Specification;

public class GetGiftCertificatesByPartNameOrDescriptionSpecification implements Specification {
    private final String name;
    private final String description;
    private final String sort;

    private static final String QUERY = "SELECT id, name, description, price, duration, " +
            "create_date, last_update_date FROM get_gifts_by_parts(?,?,?);";

    public GetGiftCertificatesByPartNameOrDescriptionSpecification(String name, String description, String sort) {
        this.name = name;
        this.description = description;
        this.sort = sort;
    }

    @Override
    public String getQuery() {
        return QUERY;
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{name, description, sort};
    }
}
