package com.epam.esm.service.builder;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.service.dto.GiftCertificateDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
public class GiftCertificateBuilder {

  public GiftCertificate buildWithoutTags(GiftCertificateDto giftCertificateDto) {
    return new GiftCertificate(
        giftCertificateDto.getId(),
        giftCertificateDto.getName(),
        giftCertificateDto.getDescription(),
        giftCertificateDto.getPrice(),
        giftCertificateDto.getDuration(),
        giftCertificateDto.getCreateDate(),
        giftCertificateDto.getLastUpdateDate());
  }

  public GiftCertificate buildNewParameterGiftCertificate(
      GiftCertificate existingGiftCertificate, GiftCertificate giftCertificate) {
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

  public GiftCertificate build(GiftCertificateDto giftCertificateDto) {
    GiftCertificate giftCertificate = getGiftCertificate(giftCertificateDto);
    giftCertificate.setTags(
        giftCertificateDto.getTags().stream()
            .map(tagDto -> new TagBuilder().build(tagDto))
            .collect(Collectors.toSet()));
    return giftCertificate;
  }

  private GiftCertificate getGiftCertificate(GiftCertificateDto giftCertificateDto) {
    GiftCertificate giftCertificate = new GiftCertificate();
    giftCertificate.setId(giftCertificateDto.getId());
    giftCertificate.setName(giftCertificateDto.getName());
    giftCertificate.setDescription(giftCertificateDto.getDescription());
    giftCertificate.setPrice(giftCertificateDto.getPrice());
    giftCertificate.setDuration(giftCertificateDto.getDuration());
    giftCertificate.setCreateDate(giftCertificateDto.getCreateDate());
    giftCertificate.setLastUpdateDate(giftCertificateDto.getLastUpdateDate());
    return giftCertificate;
  }
}
