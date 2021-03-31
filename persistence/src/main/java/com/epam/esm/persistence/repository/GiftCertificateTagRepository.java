package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.GiftCertificateTag;

import java.util.Optional;

public interface GiftCertificateTagRepository extends Repository<GiftCertificateTag> {
    Optional<GiftCertificateTag> save(GiftCertificateTag giftCertificateTag);

    Optional<GiftCertificateTag> update(GiftCertificateTag giftCertificateTag);
}
