package com.epam.esm.dao.repository;

import com.epam.esm.dao.entity.GiftCertificate;

import java.util.Optional;

/**
 * * This interface describes a common operations with GiftCertificate's Entities situated in the DB
 */
public interface GiftCertificateRepository extends Repository<GiftCertificate> {
  /**
   * * This method describes a general save (create) operation for all Tags, located in the DB
   *
   * @param giftCertificate - GiftCertificate, which transmitted in the method as a args
   * @return Optional<GiftCertificate> - container that is contained GiftCertificate
   */
  Optional<GiftCertificate> save(GiftCertificate giftCertificate);
  /**
   * * Updating the Gift Certificate with the parameters passed in the Entity
   *
   * @param giftCertificate - passed into the method GiftCertificate that is contained in one of all
   *     tables in the DB
   * @return Optional<GiftCertificate> - container that is contained GiftCertificate
   */
  Optional<GiftCertificate> update(GiftCertificate giftCertificate);
  /**
   * * This method describes a general delete operation for all GiftCertificates with the specified
   * id, located in the DB
   *
   * @param id id - passed into the method id parameter that is contained in one of all tables in
   *     the DB
   * @return int - return value more than 0, when Tag was deleted
   */
  int delete(long id);
}
