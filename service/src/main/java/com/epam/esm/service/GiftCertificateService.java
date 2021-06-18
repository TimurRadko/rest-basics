package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDto;

import java.util.List;
import java.util.Optional;

/** * This interface describes specific realization of CRUD operation on GiftCertificate Entities */
public interface GiftCertificateService extends Service<GiftCertificateDto> {
  /**
   * * This method describes a general save (create) operation for all GiftCertificateDtos, located
   * in the DB
   *
   * @param giftCertificateDto - GiftCertificateDto, which transmitted in the method as a args
   * @return Optional<GiftCertificateDto> - container that is contained GiftCertificateDto
   */
  Optional<GiftCertificateDto> save(GiftCertificateDto giftCertificateDto);
  /**
   * * This method describes a general getting a list of all entities by parameters entity. This
   * operation for all Entities, from the persistence layer
   *
   * @param page - the parameter describes current page
   * @param size - the parameter describes quantity of the GiftCertificates for one page
   * @param name - the parameter required to search for GiftCertificate in the name field of this
   *     object
   * @param description - the parameter required to search for GiftCertificate in the describe field
   *     * of this object
   * @param tagNames - List of tag names necessary for searching GiftCertificate for all these names
   * @param sorts - List of sorts the, which describes rules of sorting getting data
   * @return List<GiftCertificateDto> - List of GiftCertificateDto (this is Entity describes the
   *     data-transfer object for working with representation entities necessary for public API)
   */
  List<GiftCertificateDto> getAllByParams(
      Integer page,
      Integer size,
      String name,
      String description,
      List<String> tagNames,
      List<String> sorts);
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

  /**
   * * This method describes update operation in the GiftCertificateDto (update one passed field)
   *
   * @param id - passed into the method id parameter necessary for searching matched Entity
   * @param giftCertificateDto - GiftCertificateDto necessary for update this Entity logic
   * @return Optional<GiftCertificateDto> - container that is contained Entity (this is Entity
   *     describes the data-transfer object for working with representation entities necessary for
   *     public API
   */
  Optional<GiftCertificateDto> updateOneField(long id, GiftCertificateDto giftCertificateDto);

  /**
   * * This method describes a general delete (deleting a GitCertificate by parameter) operation for
   * all GiftCertificates, from persistence layer
   *
   * @param id - passed into the method id GiftCertificate's parameter that required for work with
   *     the DB
   * @return int - return value more than 0, when Entity was deleted
   */
  int delete(long id);
}
