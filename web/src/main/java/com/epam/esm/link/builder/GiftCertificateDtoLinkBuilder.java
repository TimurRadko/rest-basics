package com.epam.esm.link.builder;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.controller.GiftCertificateController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateDtoLinkBuilder implements LinkBuilder<GiftCertificateDto> {
  private final LinkBuilder<TagDto> tagDtoLinkBuilder;

  @Autowired
  public GiftCertificateDtoLinkBuilder(LinkBuilder<TagDto> tagDtoLinkBuilder) {
    this.tagDtoLinkBuilder = tagDtoLinkBuilder;
  }

  @Override
  public GiftCertificateDto build(GiftCertificateDto giftCertificateDto) {
    addLinkToTagDtos(giftCertificateDto);
    return giftCertificateDto.add(
        linkTo(methodOn(GiftCertificateController.class).get(giftCertificateDto.getId()))
            .withSelfRel());
  }

  private void addLinkToTagDtos(GiftCertificateDto giftCertificateDto) {
    giftCertificateDto.setTags(
        giftCertificateDto.getTags().stream()
            .map(tagDtoLinkBuilder::build)
            .collect(Collectors.toSet()));
  }
}
