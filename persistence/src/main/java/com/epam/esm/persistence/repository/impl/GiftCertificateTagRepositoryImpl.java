package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.entity.GiftCertificateTag;
import com.epam.esm.persistence.repository.GiftCertificateTagRepository;
import com.epam.esm.persistence.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateTagRepositoryImpl implements GiftCertificateTagRepository {
    private JdbcTemplate jdbcTemplate;
    private static final String RETURNING = "RETURNING id, gift_certificate_id, tag_id;";

    private static final String INSERT = "INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id) " +
            "VALUES (?,?) " + RETURNING;

    @Autowired
    public GiftCertificateTagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GiftCertificateTag> getEntitiesListBySpecification(Specification specification) {
        return jdbcTemplate.query(
                specification.getQuery(),
                specification.getArgs(),
                new BeanPropertyRowMapper<>(GiftCertificateTag.class));
    }

    @Override
    public Optional<GiftCertificateTag> getEntityBySpecification(Specification specification) {
        try {
            GiftCertificateTag giftCertificateTag = jdbcTemplate.queryForObject(
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
        try {
            GiftCertificateTag createdGiftCertificateTag = jdbcTemplate.queryForObject(INSERT,
                    new Object[]{
                            giftCertificateTag.getGiftCertificateId(),
                            giftCertificateTag.getTagId()},
                    new BeanPropertyRowMapper<>(GiftCertificateTag.class));
            return Optional.ofNullable(createdGiftCertificateTag);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
