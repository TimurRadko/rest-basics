package com.epam.esm.dao.repository;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.specification.Specification;

import java.util.List;
import java.util.Optional;

/**
 * * This interface describes a common operations with GiftCertificate's Entities situated in the DB
 */
public interface GiftCertificateRepository {
  /**
   * * Updating the Gift Certificate with the parameters passed in the Entity
   *
   * @param giftCertificate - passed into the method GiftCertificate that is contained in one of all
   *     tables in the DB
   * @return Optional<T> - container that is contained Entity (typed parameter)
   */
  Optional<GiftCertificate> update(GiftCertificate giftCertificate);

  Optional<GiftCertificate> save(GiftCertificate giftCertificate);

  List<GiftCertificate> getGiftCertificatesBySpecification(Specification specification);

  List<GiftCertificate> getSortedGiftCertificates(String sort);

  Optional<GiftCertificate> getGiftCertificateById(long id);

  int delete(long id);
}
