package com.epam.esm.service.builder.certificate;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.service.builder.tag.TagBuilder;
import com.epam.esm.service.dto.GiftCertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
public class GiftCertificateBuilder {
  private final TagBuilder tagBuilder;

  @Autowired
  public GiftCertificateBuilder(TagBuilder tagBuilder) {
    this.tagBuilder = tagBuilder;
  }

  public GiftCertificate buildNewParameterGiftCertificate(
      GiftCertificate existingGiftCertificate, GiftCertificateDto giftCertificateDto) {
    String name = giftCertificateDto.getName();
    String description = giftCertificateDto.getDescription();
    BigDecimal price = giftCertificateDto.getPrice();
    Integer duration = giftCertificateDto.getDuration();
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
    setTagsFromTagDtos(existingGiftCertificate, giftCertificateDto);
    return existingGiftCertificate;
  }

  public GiftCertificate build(GiftCertificateDto giftCertificateDto) {
    GiftCertificate giftCertificate = getGiftCertificate(giftCertificateDto);
    setTagsFromTagDtos(giftCertificate, giftCertificateDto);
    return giftCertificate;
  }

  private void setTagsFromTagDtos(
      GiftCertificate giftCertificate, GiftCertificateDto giftCertificateDto) {
    giftCertificate.setTags(
        giftCertificateDto.getTags().stream().map(tagBuilder::build).collect(Collectors.toSet()));
  }

  private GiftCertificate getGiftCertificate(GiftCertificateDto giftCertificateDto) {
    return new GiftCertificate(
        giftCertificateDto.getId(),
        giftCertificateDto.getName(),
        giftCertificateDto.getDescription(),
        giftCertificateDto.getPrice(),
        giftCertificateDto.getDuration(),
        giftCertificateDto.getCreateDate(),
        giftCertificateDto.getLastUpdateDate());
  }
}
