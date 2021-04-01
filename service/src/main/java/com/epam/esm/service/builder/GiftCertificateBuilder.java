package com.epam.esm.service.builder;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.service.dto.GiftCertificateDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GiftCertificateBuilder {

    public GiftCertificate buildFromDto(GiftCertificateDto giftCertificateDto) {
        return new GiftCertificate(giftCertificateDto.getId(),
                giftCertificateDto.getName(),
                giftCertificateDto.getDescription(),
                giftCertificateDto.getPrice(),
                giftCertificateDto.getDuration(),
                giftCertificateDto.getCreateDate(),
                giftCertificateDto.getLastUpdateDate());
    }

    public GiftCertificate buildNewParameterGiftCertificate(GiftCertificate existingGiftCertificate,
                                                            GiftCertificate giftCertificate) {
        String name = giftCertificate.getName();
        String description = giftCertificate.getDescription();
        BigDecimal price = giftCertificate.getPrice();
        Integer duration = giftCertificate.getDuration();
        if (name != null) {
            existingGiftCertificate.setName(name);
        }
        if (description != null) {
            existingGiftCertificate.setDescription(description);
        }
        if (price != null) {
            existingGiftCertificate.setPrice(price);
        }
        if (duration != null) {
            existingGiftCertificate.setDuration(duration);
        }
        return existingGiftCertificate;
    }
}
