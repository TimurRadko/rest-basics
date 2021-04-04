package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDto;

import java.util.List;
import java.util.Optional;

/***
 * This interface describes specific realization of CRUD operation on GiftCertificate Entities
 */
public interface GiftCertificateService extends Service<GiftCertificateDto> {
    /***
     * This method describes a general getting a list of all entities by field name of the Tag entity
     * operation for all Entities, from persistence layer
     *
     * @param giftCertificateDtos List<GiftCertificateDto> - List of GiftCertificateDto necessary for
     *                            searching matching Entities with parameter TagName
     * @param TagName - the parameter necessary for searching GiftCertificate for Tag name
     * @param sort - the parameter, which describes rules of sorting getting data
     * @return List<GiftCertificateDto> - List of GiftCertificateDto (this is Entity describes
     * the data-transfer object for working with representation entities necessary for public API)
     */
    List<GiftCertificateDto> getGiftCertificatesByTagName(List<GiftCertificateDto> giftCertificateDtos,
                                                          String TagName,
                                                          String sort);

    /***
     * This method describes getting a list of all entities by part of name or/and description fields
     * operation for all Entities, from persistence layer
     *
     * @param name - - the parameter required to search for GiftCertificate in the name field of this object
     * @param description - the parameter required to search for GiftCertificate
     *                    in the describe field of this object
     * @param sort - the parameter, which describes rules of sorting getting data
     * @return List<GiftCertificateDto> - List of GiftCertificateDto (this is Entity describes
     * the data-transfer object for working with representation entities necessary for public API)
     */
    List<GiftCertificateDto> getGiftCertificatesByNameOrDescriptionPart(String name,
                                                                        String description,
                                                                        String sort);

    /***
     * This method describes update operation with the parameters passed in the GiftCertificateDto
     *
     * @param id - passed into the method id parameter necessary for searching matched Entity
     * @param giftCertificateDto - GiftCertificateDto necessary for update this Entity logic
     * @return Optional<GiftCertificateDto> - container that is contained Entity (this is Entity describes
     * the data-transfer object for working with representation entities necessary for public API
     */
    Optional<GiftCertificateDto> update(long id, GiftCertificateDto giftCertificateDto);
}
