package com.epam.esm.dao.specification.order;

import com.epam.esm.dao.entity.Order;
import com.epam.esm.dao.specification.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class GetOrderByIdSpecification implements Specification<Order> {
    private final long id;

    public GetOrderByIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public CriteriaQuery<Order> getCriteriaQuery(CriteriaBuilder builder) {
        return null;
    }
}
