package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDto;

import java.util.List;
import java.util.Optional;

/** * This interface describes specific realization of CRUD operation on GiftCertificate Entities */
public interface GiftCertificateService extends Service<GiftCertificateDto> {
  /**
   * * This method describes a general getting a list of all entities by parameters entity. This
   * operation for all Entities, from the persistence layer
   *
   * @param name - the parameter required to search for GiftCertificate in the name field of this *
   *     object
   * @param description - the parameter required to search for GiftCertificate in the describe field
   *     * of this object
   * @param tagName - the parameter necessary for searching GiftCertificate for Tag name
   * @param sort - the parameter, which describes rules of sorting getting data
   * @return List<GiftCertificateDto> - List of GiftCertificateDto (this is Entity describes the
   *     data-transfer object for working with representation entities necessary for public API)
   */
  List<GiftCertificateDto> getAllByParams(
      String name, String description, String tagName, String sort);
  /**
   * * This method describes update operation with the parameters passed in the GiftCertificateDto
   *
   * @param id - passed into the method id parameter necessary for searching matched Entity
   * @param giftCertificateDto - GiftCertificateDto necessary for update this Entity logic
   * @return Optional<GiftCertificateDto> - container that is contained Entity (this is Entity
   *     describes the data-transfer object for working with representation entities necessary for
   *     public API
   */
  Optional<GiftCertificateDto> update(long id, GiftCertificateDto giftCertificateDto);
}
