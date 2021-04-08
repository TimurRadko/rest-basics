package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.repository.TagRepository;
import com.epam.esm.dao.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {
  private JdbcTemplate jdbcTemplate;

  private static final String INSERT = "INSERT INTO tags (name) VALUES (?);";

  private static final String DELETE_TAG_FROM_GIFT_CERTIFICATES =
      "DELETE FROM gift_certificates_tags WHERE tag_id = ?;";

  private static final String DELETE_TAG_BY_ID = "DELETE FROM tags WHERE id = ?;";

  @Autowired
  public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<Tag> getEntityListBySpecification(Specification specification) {
    return jdbcTemplate.query(
        specification.getQuery(), specification.getArgs(), new BeanPropertyRowMapper<>(Tag.class));
  }

  @Override
  public Optional<Tag> getEntityBySpecification(Specification specification) {
    try {
      Tag tag =
          jdbcTemplate.queryForObject(
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
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(
        connection -> {
          PreparedStatement ps =
              connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
          ps.setString(1, tag.getName());
          return ps;
        },
        keyHolder);
    if (keyHolder.getKeys() == null) {
      return Optional.empty();
    }
    tag.setId((Long) keyHolder.getKeys().get("id"));
    return Optional.of(tag);
  }

  @Override
  public int delete(long id) {
    jdbcTemplate.update(DELETE_TAG_FROM_GIFT_CERTIFICATES, id);
    return jdbcTemplate.update(DELETE_TAG_BY_ID, id);
  }
}
