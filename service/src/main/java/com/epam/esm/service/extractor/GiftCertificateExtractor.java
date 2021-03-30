package com.epam.esm.service.extractor;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.service.dto.GiftCertificateDto;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateExtractor {

    public GiftCertificate extract(GiftCertificateDto giftCertificateDto) {
        return new GiftCertificate(giftCertificateDto.getId(),
                giftCertificateDto.getName(),
                giftCertificateDto.getDescription(),
                giftCertificateDto.getPrice(),
                giftCertificateDto.getDuration(),
                giftCertificateDto.getCreateDate(),
                giftCertificateDto.getLastUpdateDate());
    }
}
