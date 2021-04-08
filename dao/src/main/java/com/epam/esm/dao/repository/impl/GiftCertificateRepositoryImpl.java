package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.repository.GiftCertificateRepository;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
  private JdbcTemplate jdbcTemplate;

  private static final String INSERT =
      "INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date)"
          + " VALUES (?,?,?,?,?,?);";

  private static final String UPDATE =
      "UPDATE  gift_certificates SET (name, description, price, duration, last_update_date) "
          + " = (?,?,?,?,?) WHERE id=?;";

  private static final String DELETE_TAGS_FROM_GIFT_CERTIFICATE =
      "DELETE FROM gift_certificates_tags WHERE gift_certificate_id=?;";

  private static final String DELETE_GIFT_CERTIFICATE_BY_ID =
      "DELETE FROM  gift_certificates WHERE id=?;";

  @Autowired
  public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<GiftCertificate> getEntityListBySpecification(Specification specification) {
    return jdbcTemplate.query(
        specification.getQuery(),
        specification.getArgs(),
        new BeanPropertyRowMapper<>(GiftCertificate.class));
  }

  @Override
  public Optional<GiftCertificate> getEntityBySpecification(Specification specification) {
    try {
      GiftCertificate giftCertificate =
          jdbcTemplate.queryForObject(
              specification.getQuery(),
              specification.getArgs(),
              new BeanPropertyRowMapper<>(GiftCertificate.class));
      return Optional.ofNullable(giftCertificate);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<GiftCertificate> save(GiftCertificate giftCertificate) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    LocalDateTime now = LocalDateTime.now();
    jdbcTemplate.update(
        connection -> {
          PreparedStatement ps =
              connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
          ps.setString(1, giftCertificate.getName());
          ps.setString(2, giftCertificate.getDescription());
          ps.setBigDecimal(3, giftCertificate.getPrice());
          ps.setInt(4, giftCertificate.getDuration());
          ps.setTimestamp(5, Timestamp.valueOf(now));
          ps.setTimestamp(6, Timestamp.valueOf(now));
          return ps;
        },
        keyHolder);
    if (keyHolder.getKeys() == null) {
      return Optional.empty();
    }
    giftCertificate.setId((Long) keyHolder.getKeys().get("id"));
    giftCertificate.setCreateDate(now);
    giftCertificate.setLastUpdateDate(now);

    return Optional.of(giftCertificate);
  }

  @Override
  public int delete(long id) {
    jdbcTemplate.update(DELETE_TAGS_FROM_GIFT_CERTIFICATE, id);
    return jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_BY_ID, id);
  }

  @Override
  public Optional<GiftCertificate> update(GiftCertificate giftCertificate) {
    jdbcTemplate.update(
        UPDATE,
        giftCertificate.getName(),
        giftCertificate.getDescription(),
        giftCertificate.getPrice(),
        giftCertificate.getDuration(),
        Timestamp.valueOf(LocalDateTime.now()),
        giftCertificate.getId());
    return Optional.of(giftCertificate);
  }
}
