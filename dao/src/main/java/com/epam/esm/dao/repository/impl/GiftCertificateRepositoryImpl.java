package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.repository.GiftCertificateRepository;
import com.epam.esm.dao.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
  private JdbcTemplate jdbcTemplate;

  private static final String RETURNING =
      "RETURNING id, name, description, price, duration, create_date, last_update_date;";

  private static final String INSERT =
      "INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date)"
          + " VALUES (?,?,?,?,now(),now()) "
          + RETURNING;

  private static final String UPDATE =
      "UPDATE  gift_certificates SET (name, description, price, duration, last_update_date) "
          + " = (?,?,?,?,now()) WHERE id=? "
          + RETURNING;

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
    try {
      GiftCertificate createdGiftCertificate =
          jdbcTemplate.queryForObject(
              INSERT,
              new Object[] {
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration()
              },
              new BeanPropertyRowMapper<>(GiftCertificate.class));
      return Optional.ofNullable(createdGiftCertificate);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public int delete(long id) {
    jdbcTemplate.update(DELETE_TAGS_FROM_GIFT_CERTIFICATE, id);
    return jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_BY_ID, id);
  }

  @Override
  public Optional<GiftCertificate> update(GiftCertificate giftCertificate) {
    try {
      GiftCertificate updatedGiftCertificate =
          jdbcTemplate.queryForObject(
              UPDATE,
              new Object[] {
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getId()
              },
              new BeanPropertyRowMapper<>(GiftCertificate.class));
      return Optional.ofNullable(updatedGiftCertificate);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }
}
