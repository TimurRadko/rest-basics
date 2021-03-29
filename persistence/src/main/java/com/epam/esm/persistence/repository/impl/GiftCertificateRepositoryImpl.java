package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String RETURNING =
            "RETURNING id, name, description, price, duration, create_date, last_update_date;";

    private static final String INSERT_GIFT_CERTIFICATE =
            "INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date)" +
                    " VALUES (?,?,?,?,now(),now()) "
                    + RETURNING;

    @Autowired
    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GiftCertificate> getListBySpecification(Specification specification) {
        return jdbcTemplate.query(specification.getQuery(),
                new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public Optional<GiftCertificate> getEntityBySpecification(Specification specification) {
        try {
            GiftCertificate giftCertificate = jdbcTemplate.queryForObject(
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
            GiftCertificate createdGiftCertificate = jdbcTemplate.queryForObject(INSERT_GIFT_CERTIFICATE,
                    new Object[]{giftCertificate.getName(),
                            giftCertificate.getDescription(),
                            giftCertificate.getPrice(),
                            giftCertificate.getDuration(),
                            giftCertificate.getCreateDate(),
                            giftCertificate.getLastUpdateDate()},
                    new BeanPropertyRowMapper<>(GiftCertificate.class));
            return Optional.ofNullable(createdGiftCertificate);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {

    }

    @Override
    public Optional<GiftCertificate> update(GiftCertificate giftCertificate) {
        return Optional.empty();
    }
}
