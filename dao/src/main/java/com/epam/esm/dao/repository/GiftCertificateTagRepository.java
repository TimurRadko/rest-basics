package com.epam.esm.dao.repository;

import com.epam.esm.dao.entity.GiftCertificateTag;
import com.epam.esm.dao.specification.Specification;

import java.util.List;
import java.util.Optional;

/**
 * * This interface describes a common operations with GiftCertificateTag's Entities situated in the
 * DB
 */
public interface GiftCertificateTagRepository {

  Optional<GiftCertificateTag> save(GiftCertificateTag giftCertificateTag);

  List<GiftCertificateTag> getGiftCertificateTags();

  List<GiftCertificateTag> getGiftCertificateTagsByGiftCertificateId(long id);

  Optional<GiftCertificateTag> getGiftCertificateTagByGiftCertificateIdAndTagId(
      long giftCertificateId, long tagId);

  Optional<GiftCertificateTag> getGiftCertificateTagById(long id);

  int delete(long id);
}
