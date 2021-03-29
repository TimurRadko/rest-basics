package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.specification.Specification;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
                specification.getArgs(),
                new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Optional<Tag> getEntityBySpecification(Specification specification) {
        try {
            Tag tag = jdbcTemplate.queryForObject(
                    specification.getQuery(),
                    specification.getArgs(),
                    new BeanPropertyRowMapper<>(Tag.class));
            return Optional.ofNullable(tag);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Tag> save(Tag tag) {
        return Optional.empty();
    }

    @Override
    public void delete(Tag tag) {

    }
}
