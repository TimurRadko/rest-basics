package com.epam.esm.service.builder.certificate;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.service.builder.tag.TagDtoBuilder;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GiftCertificateDtoBuilder {
  private final TagDtoBuilder tagDtoBuilder;

  @Autowired
  public GiftCertificateDtoBuilder(TagDtoBuilder tagDtoBuilder) {
    this.tagDtoBuilder = tagDtoBuilder;
  }

  public GiftCertificateDto build(GiftCertificate giftCertificate) {
    GiftCertificateDto giftCertificateDto = getGiftCertificateDto(giftCertificate);
    giftCertificateDto.setTags(
        giftCertificate.getTags().stream()
            .map(tagDtoBuilder::build)
            .collect(Collectors.toSet()));
    return giftCertificateDto;
  }

  public GiftCertificateDto buildWithTagDtos(GiftCertificate giftCertificate, Set<TagDto> tags) {
    GiftCertificateDto giftCertificateDto = getGiftCertificateDto(giftCertificate);
    giftCertificateDto.setTags(tags);
    return giftCertificateDto;
  }

  private GiftCertificateDto getGiftCertificateDto(GiftCertificate giftCertificate) {
    GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
    giftCertificateDto.setId(giftCertificate.getId());
    giftCertificateDto.setName(giftCertificate.getName());
    giftCertificateDto.setDescription(giftCertificate.getDescription());
    giftCertificateDto.setPrice(giftCertificate.getPrice());
    giftCertificateDto.setDuration(giftCertificate.getDuration());
    giftCertificateDto.setCreateDate(giftCertificate.getCreateDate());
    giftCertificateDto.setLastUpdateDate(giftCertificate.getLastUpdateDate());
    return giftCertificateDto;
  }
}
