package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.GiftCertificateTag;
import com.epam.esm.dao.repository.GiftCertificateTagRepository;
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
public class GiftCertificateTagRepositoryImpl implements GiftCertificateTagRepository {
  private JdbcTemplate jdbcTemplate;

  private static final String INSERT =
      "INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id) VALUES (?,?);";

  @Autowired
  public GiftCertificateTagRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<GiftCertificateTag> getEntityListBySpecification(Specification specification) {
    return jdbcTemplate.query(
        specification.getQuery(),
        specification.getArgs(),
        new BeanPropertyRowMapper<>(GiftCertificateTag.class));
  }

  @Override
  public Optional<GiftCertificateTag> getEntityBySpecification(Specification specification) {
    try {
      GiftCertificateTag giftCertificateTag =
          jdbcTemplate.queryForObject(
              specification.getQuery(),
              specification.getArgs(),
              new BeanPropertyRowMapper<>(GiftCertificateTag.class));
      return Optional.ofNullable(giftCertificateTag);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<GiftCertificateTag> save(GiftCertificateTag giftCertificateTag) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(
        connection -> {
          PreparedStatement ps =
              connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
          ps.setLong(1, giftCertificateTag.getGiftCertificateId());
          ps.setLong(2, giftCertificateTag.getTagId());
          return ps;
        },
        keyHolder);
    if (keyHolder.getKeys() == null) {
      return Optional.empty();
    }
    giftCertificateTag.setId((Long) keyHolder.getKeys().get("id"));
    return Optional.of(giftCertificateTag);
  }
}
