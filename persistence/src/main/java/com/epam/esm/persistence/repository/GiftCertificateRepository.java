package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.GiftCertificate;

public interface GiftCertificateRepository extends Repository<GiftCertificate> {
    GiftCertificate update(GiftCertificate giftCertificate);
}
