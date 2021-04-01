package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.GiftCertificate;

import java.util.Optional;

public interface GiftCertificateRepository extends Repository<GiftCertificate> {
    Optional<GiftCertificate> update(GiftCertificate giftCertificate);

    void delete(long id);
}
