package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateService extends Service<GiftCertificateDto> {
    List<GiftCertificateDto> getGiftCertificatesByTagName(String name);
}
