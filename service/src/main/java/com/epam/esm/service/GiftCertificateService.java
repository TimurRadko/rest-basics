package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDto;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService extends Service<GiftCertificateDto> {
    List<GiftCertificateDto> getGiftCertificatesByTagName(String name);
    Optional<GiftCertificateDto> update(long id, GiftCertificateDto giftCertificateDto);
}
