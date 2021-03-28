package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.specification.Specification;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private final JdbcTemplate jdbcTemplate;

    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> getListBySpecification(Specification specification) {
        return jdbcTemplate.query(
                specification.getQuery(),
                new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Tag getEntityBySpecification(Specification specification) {
        return jdbcTemplate.queryForObject(
                specification.getQuery(),
                specification.getArgs(),
                new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Tag create(Tag tag) {
        return null;
    }

    @Override
    public void delete(Tag tag) {

    }
}
