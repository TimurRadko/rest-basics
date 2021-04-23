package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.repository.GiftCertificateRepository;
import com.epam.esm.dao.repository.TagRepository;
import com.epam.esm.dao.specification.gift.GetAllGiftCertificatesSpecification;
import com.epam.esm.dao.specification.gift.GetGiftCertificatesByDescriptionPartSpecification;
import com.epam.esm.dao.specification.gift.GetGiftCertificatesByIdSpecification;
import com.epam.esm.dao.specification.gift.GetGiftCertificatesByNamePartSpecification;
import com.epam.esm.dao.specification.gift.GetGiftCertificatesByTagNameSpecification;
import com.epam.esm.dao.specification.tag.GetAllTagsByGiftCertificatesIdSpecification;
import com.epam.esm.dao.specification.tag.GetTagByIdSpecification;
import com.epam.esm.dao.specification.tag.GetTagByNameSpecification;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.builder.certificate.GiftCertificateBuilder;
import com.epam.esm.service.builder.certificate.GiftCertificateDtoBuilder;
import com.epam.esm.service.builder.tag.TagBuilder;
import com.epam.esm.service.builder.tag.TagDtoBuilder;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.GiftCertificatePriceDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.EntityNotValidException;
import com.epam.esm.service.exception.EntityNotValidMultipleException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.GiftCertificatePriceValidator;
import com.epam.esm.service.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
  private final GiftCertificateRepository giftCertificateRepository;
  private final TagRepository tagRepository;
  private final GiftCertificateValidator giftCertificateValidator;
  private final GiftCertificatePriceValidator giftCertificatePriceValidator;
  private final GiftCertificateBuilder giftCertificateBuilder;
  private final GiftCertificateDtoBuilder giftCertificateDtoBuilder;
  private final TagBuilder tagBuilder;
  private final TagDtoBuilder tagDtoBuilder;

  @Autowired
  public GiftCertificateServiceImpl(
      GiftCertificateRepository giftCertificateRepository,
      TagRepository tagRepository,
      GiftCertificateValidator giftCertificateValidator,
      GiftCertificatePriceValidator giftCertificatePriceValidator,
      GiftCertificateBuilder giftCertificateBuilder,
      GiftCertificateDtoBuilder giftCertificateDtoBuilder,
      TagBuilder tagBuilder,
      TagDtoBuilder tagDtoBuilder) {
    this.giftCertificateRepository = giftCertificateRepository;
    this.tagRepository = tagRepository;
    this.giftCertificateValidator = giftCertificateValidator;
    this.giftCertificatePriceValidator = giftCertificatePriceValidator;
    this.giftCertificateBuilder = giftCertificateBuilder;
    this.giftCertificateDtoBuilder = giftCertificateDtoBuilder;
    this.tagBuilder = tagBuilder;
    this.tagDtoBuilder = tagDtoBuilder;
  }

  @Override
  @Transactional
  public Optional<GiftCertificateDto> save(GiftCertificateDto giftCertificateDto) {
    if (!giftCertificateValidator.isValid(giftCertificateDto)) {
      throw new EntityNotValidMultipleException(giftCertificateValidator.getErrorMessage());
    }
    Set<TagDto> tags = giftCertificateDto.getTags();
    tags = saveTags(tags);
    giftCertificateDto.setTags(tags);
    GiftCertificate giftCertificate =
        giftCertificateRepository
            .save(giftCertificateBuilder.build(giftCertificateDto))
            .orElseThrow();
    return Optional.of(giftCertificateDtoBuilder.buildWithTagDtos(giftCertificate, tags));
  }

  private Set<TagDto> saveTags(Set<TagDto> tags) {
    Set<TagDto> createdTags = new HashSet<>();
    if (tags != null) {
      tags.forEach((tag -> createdTags.add(saveTagIfNotExist(tag))));
    }
    return createdTags;
  }

  private TagDto saveTagIfNotExist(TagDto tagDto) {
    Optional<Tag> optionalExistingTag;
    if (tagDto.getId() == null) {
      optionalExistingTag =
          tagRepository.getEntityBySpecification(new GetTagByNameSpecification(tagDto.getName()));
    } else {
      optionalExistingTag =
          tagRepository.getEntityBySpecification(new GetTagByIdSpecification(tagDto.getId()));
      if (optionalExistingTag.isEmpty()) {
        throw new EntityNotFoundException(
            "The Tag with id = " + tagDto.getId() + " cannot be created in the database");
      }
    }
    if (optionalExistingTag.isEmpty()) {
      return tagDtoBuilder.build(
          tagRepository
              .save(tagBuilder.build(tagDto))
              .orElseThrow(() -> new ServiceException("The Tag wasn't saved")));
    } else {
      return tagDtoBuilder.build(optionalExistingTag.get());
    }
  }

  @Override
  public List<GiftCertificateDto> getAllByParams(
      String name, String description, String tagName, List<String> sorts) {
    if (name == null && description == null && tagName == null) {
      return getAll(sorts);
    }
    List<GiftCertificate> giftCertificates = new ArrayList<>();
    if (name != null && name.trim().length() != 0) {
      giftCertificates =
          giftCertificateRepository.getEntityListBySpecification(
              new GetGiftCertificatesByNamePartSpecification(name, sorts));
    }
    if (description != null && description.trim().length() != 0) {
      giftCertificates = getGiftCertificatesByDescriptionPart(giftCertificates, description, sorts);
    }
    if (tagName != null) {
      giftCertificates = getGiftCertificatesByTagName(giftCertificates, tagName, sorts);
    }
    return giftCertificates.stream()
        .map(giftCertificateDtoBuilder::build)
        .collect(Collectors.toList());
  }

  private List<GiftCertificateDto> getAll(List<String> sort) {
    return giftCertificateRepository
        .getEntityListBySpecification(new GetAllGiftCertificatesSpecification(sort)).stream()
        .map((giftCertificateDtoBuilder::build))
        .collect(Collectors.toList());
  }

  private List<GiftCertificate> getGiftCertificatesByDescriptionPart(
      List<GiftCertificate> gitCertificates, String description, List<String> sorts) {
    List<GiftCertificate> findingGiftCertificates =
        giftCertificateRepository.getEntityListBySpecification(
            new GetGiftCertificatesByDescriptionPartSpecification(description, sorts));
    if (gitCertificates.size() == 0) {
      return findingGiftCertificates;
    } else {
      return findingGiftCertificates.stream()
          .filter((gitCertificates::contains))
          .collect(Collectors.toList());
    }
  }

  private List<GiftCertificate> getGiftCertificatesByTagName(
      List<GiftCertificate> giftCertificates, String tagName, List<String> sorts) {
    if (giftCertificates.size() == 0) {
      return giftCertificateRepository.getEntityListBySpecification(
          new GetGiftCertificatesByTagNameSpecification(tagName, sorts));
    }
    return giftCertificates.stream()
        .filter(
            (giftCertificateDto ->
                giftCertificateDto.getTags().stream()
                    .anyMatch(tag -> tag.getName().equalsIgnoreCase(tagName))))
        .collect(Collectors.toList());
  }

  @Override
  public Optional<GiftCertificateDto> getById(long id) {
    Optional<GiftCertificate> optionalGiftCertificate =
        giftCertificateRepository.getEntityBySpecification(
            new GetGiftCertificatesByIdSpecification(id));
    return optionalGiftCertificate.map(giftCertificateDtoBuilder::build);
  }

  @Override
  @Transactional
  public Optional<GiftCertificateDto> update(long id, GiftCertificateDto giftCertificateDto) {
    if (!giftCertificateValidator.isValid(giftCertificateDto)) {
      throw new EntityNotValidMultipleException(giftCertificateValidator.getErrorMessage());
    }
    GiftCertificate existingGiftCertificate = getExistingGiftCertificate(id);

    Set<TagDto> tagDtos = giftCertificateDto.getTags();
    tagDtos = saveTags(tagDtos);
    deletingNonTransmittedTags(id, tagDtos);
    giftCertificateDto.setTags(tagDtos);

    GiftCertificate giftCertificate =
        giftCertificateBuilder.buildNewParameterGiftCertificate(
            existingGiftCertificate, giftCertificateDto);

    return Optional.of(
        giftCertificateDtoBuilder.build(
            giftCertificateRepository
                .update(giftCertificate)
                .orElseThrow(
                    () ->
                        new EntityNotFoundException("The Gift Certificate not exists in the DB"))));
  }

  private GiftCertificate getExistingGiftCertificate(long id) {
    return giftCertificateRepository
        .getEntityBySpecification(new GetGiftCertificatesByIdSpecification(id))
        .orElseThrow(
            () -> new EntityNotFoundException("The Gift Certificate not exists in the DB"));
  }

  @Override
  public Optional<GiftCertificateDto> updatePrice(
      long id, GiftCertificatePriceDto giftCertificatePriceDto) {
    if (!giftCertificatePriceValidator.isValid(giftCertificatePriceDto)) {
      throw new EntityNotValidException(giftCertificatePriceValidator.getErrorMessage());
    }
    GiftCertificate giftCertificate = getGiftCertificateById(id);
    giftCertificate.setPrice(giftCertificatePriceDto.getPrice());
    giftCertificate = updateGiftCertificate(giftCertificate);
    return Optional.of(giftCertificateDtoBuilder.build(giftCertificate));
  }

  private GiftCertificate getGiftCertificateById(Long id) {
    return giftCertificateRepository
        .getEntityBySpecification(new GetGiftCertificatesByIdSpecification(id))
        .orElseThrow(
            () -> new EntityNotFoundException("The Gift Certificate not exists in the DB"));
  }

  private GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate) {
    return giftCertificateRepository
        .update(giftCertificate)
        .orElseThrow(
            () -> new EntityNotFoundException("The Gift Certificate not exists in the DB"));
  }

  private void deletingNonTransmittedTags(long id, Set<TagDto> tagDtos) {
    List<Tag> existingTags =
        tagRepository.getEntityListBySpecification(
            new GetAllTagsByGiftCertificatesIdSpecification(id));
    Set<Tag> tags = tagDtos.stream().map(tagBuilder::build).collect(Collectors.toSet());
    for (Tag existingTag : existingTags) {
      if (!tags.contains(existingTag)) {
        tagRepository.delete(existingTag.getId());
      }
    }
  }

  @Override
  public int delete(long id) {
    getById(id)
        .orElseThrow(() -> new ServiceException("Requested resource not found (id = " + id + ")"));
    return giftCertificateRepository.delete(id);
  }
}
