package com.epam.esm.dao.repository;

import com.epam.esm.dao.entity.GiftCertificate;

import java.util.Optional;

/**
 * * This interface describes a common operations with GiftCertificate's Entities situated in the DB
 */
public interface GiftCertificateRepository extends Repository<GiftCertificate> {
  /**
   * * Updating the Gift Certificate with the parameters passed in the Entity
   *
   * @param giftCertificate - passed into the method GiftCertificate that is contained in one of all
   *     tables in the DB
   * @return Optional<T> - container that is contained Entity (typed parameter)
   */
  Optional<GiftCertificate> update(GiftCertificate giftCertificate);
}
