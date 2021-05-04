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
    addName(existingGiftCertificate, giftCertificateDto);
    addDescription(existingGiftCertificate, giftCertificateDto);
    addPrice(existingGiftCertificate, giftCertificateDto);
    addDuration(existingGiftCertificate, giftCertificateDto);
    setTagsFromTagDtos(existingGiftCertificate, giftCertificateDto);
    return existingGiftCertificate;
  }

  private void addName(
      GiftCertificate existingGiftCertificate, GiftCertificateDto giftCertificateDto) {
    String name = giftCertificateDto.getName();
    if (name != null) {
      existingGiftCertificate.setName(name);
    }
  }

  private void addDescription(
      GiftCertificate existingGiftCertificate, GiftCertificateDto giftCertificateDto) {
    String description = giftCertificateDto.getDescription();
    if (description != null) {
      existingGiftCertificate.setDescription(description);
    }
  }

  private void addPrice(
      GiftCertificate existingGiftCertificate, GiftCertificateDto giftCertificateDto) {
    BigDecimal price = giftCertificateDto.getPrice();
    if (price != null) {
      existingGiftCertificate.setPrice(price);
    }
  }

  private void addDuration(
      GiftCertificate existingGiftCertificate, GiftCertificateDto giftCertificateDto) {
    Integer duration = giftCertificateDto.getDuration();
    if (duration != null) {
      existingGiftCertificate.setDuration(duration);
    }
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
