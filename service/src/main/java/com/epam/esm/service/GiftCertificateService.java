package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDto;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService extends Service<GiftCertificateDto> {
    List<GiftCertificateDto> getGiftCertificatesByTagName(List<GiftCertificateDto> giftCertificateDtos,
                                                          String TagName,
                                                          String sort);

    List<GiftCertificateDto> getGiftCertificatesByNameOrDescriptionPart(String name,
                                                                        String description,
                                                                        String sort);

    Optional<GiftCertificateDto> update(long id, GiftCertificateDto giftCertificateDto);
}
