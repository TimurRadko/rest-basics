package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.GiftCertificateTag;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.repository.GiftCertificateRepository;
import com.epam.esm.dao.repository.GiftCertificateTagRepository;
import com.epam.esm.dao.repository.TagRepository;
import com.epam.esm.dao.specification.gift.GetAllGiftCertificatesSpecification;
import com.epam.esm.dao.specification.gift.GetGiftCertificatesByIdSpecification;
import com.epam.esm.dao.specification.gift.GetGiftCertificatesByDescriptionPartSpecification;
import com.epam.esm.dao.specification.gift.GetGiftCertificatesByNamePartSpecification;
import com.epam.esm.dao.specification.gift.GetGiftCertificatesByTagNameSpecification;
import com.epam.esm.dao.specification.gifttag.GetGiftCertificateTagByGiftCertificateIdSpecification;
import com.epam.esm.dao.specification.tag.GetAllTagsByGiftCertificatesIdSpecification;
import com.epam.esm.dao.specification.tag.GetTagByIdSpecification;
import com.epam.esm.dao.specification.tag.GetTagByNameSpecification;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.builder.GiftCertificateBuilder;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.GiftCertificatePriceDto;
import com.epam.esm.service.exception.EntityNotValidException;
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
  private final GiftCertificateTagRepository giftCertificateTagRepository;
  private final GiftCertificateValidator giftCertificateValidator;
  private final GiftCertificatePriceValidator giftCertificatePriceValidator;
  private final GiftCertificateBuilder builder;
  private static final String CERTIFICATE_NOT_EXISTS_IN_THE_DB =
      "The Gift Certificate not exists in the DB";

  @Autowired
  public GiftCertificateServiceImpl(
      GiftCertificateRepository giftCertificateRepository,
      TagRepository tagRepository,
      GiftCertificateTagRepository giftCertificateTagRepository,
      GiftCertificateValidator giftCertificateValidator,
      GiftCertificatePriceValidator giftCertificatePriceValidator,
      GiftCertificateBuilder builder) {
    this.giftCertificateRepository = giftCertificateRepository;
    this.tagRepository = tagRepository;
    this.giftCertificateTagRepository = giftCertificateTagRepository;
    this.giftCertificateValidator = giftCertificateValidator;
    this.giftCertificatePriceValidator = giftCertificatePriceValidator;
    this.builder = builder;
  }

  @Override
  @Transactional
  public Optional<GiftCertificateDto> save(GiftCertificateDto giftCertificateDto) {
    if (!giftCertificateValidator.isValid(giftCertificateDto)) {
      throw new EntityNotValidException(giftCertificateValidator.getErrorMessage());
    }
    GiftCertificate giftCertificate = builder.buildFromDto(giftCertificateDto);
    giftCertificate =
        giftCertificateRepository
            .save(giftCertificate)
            .orElseThrow(() -> new ServiceException("The Gift Certificate wasn't saved"));

    Set<Tag> tags = giftCertificateDto.getTags();
    tags = saveTags(tags, giftCertificate.getId());

    giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
    return Optional.of(giftCertificateDto);
  }

  private Set<Tag> saveTags(Set<Tag> tags, long giftCertificateId) {
    Set<Tag> createdTags = new HashSet<>();
    if (tags != null) {
      tags.forEach((tag -> createdTags.add(saveTagIfNotExist(tag, giftCertificateId))));
    }
    return createdTags;
  }

  private Tag saveTagIfNotExist(Tag tag, long giftCertificateId) {
    Optional<Tag> optionalExistingTag;
    if (tag.getId() == null) {
      optionalExistingTag =
          tagRepository.getEntityBySpecification(new GetTagByNameSpecification(tag.getName()));
    } else {
      optionalExistingTag =
          tagRepository.getEntityBySpecification(new GetTagByIdSpecification(tag.getId()));
    }
    if (optionalExistingTag.isEmpty()) {
      Tag createdTag =
          tagRepository.save(tag).orElseThrow(() -> new ServiceException("The Tag wasn't saved"));

      saveGiftCertificateTag(giftCertificateId, createdTag);
      return createdTag;
    } else {
      Optional<GiftCertificateTag> optionalGiftCertificateTag =
          giftCertificateTagRepository.getEntityBySpecification(
              new GetGiftCertificateTagByGiftCertificateIdSpecification(
                  giftCertificateId, optionalExistingTag.get().getId()));
      if (optionalGiftCertificateTag.isEmpty()) {
        Tag existingTag = optionalExistingTag.get();
        saveGiftCertificateTag(giftCertificateId, existingTag);
        return existingTag;
      }
    }
    return optionalExistingTag.get();
  }

  private void saveGiftCertificateTag(long giftCertificateId, Tag tag) {
    GiftCertificateTag giftCertificateTag = new GiftCertificateTag();
    giftCertificateTag.setTagId(tag.getId());
    giftCertificateTag.setGiftCertificateId(giftCertificateId);
    giftCertificateTagRepository.save(giftCertificateTag);
  }

  @Override
  public List<GiftCertificateDto> getAllByParams(
      String name, String description, String tagName, String sort) {
    if (name == null && description == null && tagName == null) {
      return getAll(sort);
    }
    List<GiftCertificateDto> giftCertificateDtos =
        getGiftCertificatesByNameOrDescriptionPart(name, description, sort);
    if (tagName != null) {
      giftCertificateDtos = getGiftCertificateListByTagName(giftCertificateDtos, tagName, sort);
    }
    return giftCertificateDtos;
  }

  private List<GiftCertificateDto> getAll(String sort) {
    return giftCertificateRepository
        .getEntityListBySpecification(new GetAllGiftCertificatesSpecification(sort)).stream()
        .map((this::createGiftCertificateDto))
        .collect(Collectors.toList());
  }

  private List<GiftCertificateDto> getGiftCertificatesByNameOrDescriptionPart(
      String name, String description, String sort) {

    List<GiftCertificate> giftCertificates = new ArrayList<>();
    if (name != null && name.trim().length() != 0) {
      giftCertificates =
          giftCertificateRepository.getEntityListBySpecification(
              new GetGiftCertificatesByNamePartSpecification(name, sort));
    }
    if (description != null && description.trim().length() != 0) {
      List<GiftCertificate> partDescriptionGiftCertificates =
          giftCertificateRepository.getEntityListBySpecification(
              new GetGiftCertificatesByDescriptionPartSpecification(description, sort));
      giftCertificates =
          giftCertificates.stream()
              .filter((partDescriptionGiftCertificates::contains))
              .collect(Collectors.toList());
    }
    return giftCertificates.stream()
        .map((this::createGiftCertificateDto))
        .collect(Collectors.toList());
  }

  private List<GiftCertificateDto> getGiftCertificateListByTagName(
      List<GiftCertificateDto> giftCertificateDtos, String tagName, String sort) {
    if (giftCertificateDtos.size() == 0) {
      List<GiftCertificate> giftCertificates =
          giftCertificateRepository.getEntityListBySpecification(
              new GetGiftCertificatesByTagNameSpecification(tagName, sort));
      return giftCertificates.stream()
          .map((this::createGiftCertificateDto))
          .collect(Collectors.toList());
    }

    return giftCertificateDtos.stream()
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
//    if (optionalGiftCertificate.isPresent()) {
//      GiftCertificate giftCertificate = optionalGiftCertificate.get();
//      return Optional.of(createGiftCertificateDto(giftCertificate));
//    }
//    return Optional.empty();
    return optionalGiftCertificate.map(GiftCertificateDto::new);
  }

  private GiftCertificateDto createGiftCertificateDto(GiftCertificate giftCertificate) {
    long id = giftCertificate.getId();

    List<Tag> tags =
        tagRepository.getEntityListBySpecification(
            new GetAllTagsByGiftCertificatesIdSpecification(id));
    return new GiftCertificateDto(giftCertificate, Set.copyOf(tags));
  }

  @Override
  @Transactional
  public Optional<GiftCertificateDto> update(long id, GiftCertificateDto giftCertificateDto) {
    if (!giftCertificateValidator.isValid(giftCertificateDto)) {
      throw new EntityNotValidException(giftCertificateValidator.getErrorMessage());
    }
    GiftCertificate giftCertificate = builder.buildFromDto(giftCertificateDto);
    GiftCertificate existingGiftCertificate =
        giftCertificateRepository
            .getEntityBySpecification(new GetGiftCertificatesByIdSpecification(id))
            .orElseThrow(() -> new ServiceException(CERTIFICATE_NOT_EXISTS_IN_THE_DB));

    giftCertificate =
        builder.buildNewParameterGiftCertificate(existingGiftCertificate, giftCertificate);
    GiftCertificate newGiftCertification =
        giftCertificateRepository
            .update(giftCertificate)
            .orElseThrow(() -> new ServiceException(CERTIFICATE_NOT_EXISTS_IN_THE_DB));

    Set<Tag> tags = giftCertificateDto.getTags();
    tags = saveTags(tags, newGiftCertification.getId());

    deletingNonTransmittedTags(id, tags);

    giftCertificateDto = new GiftCertificateDto(newGiftCertification, tags);
    return Optional.of(giftCertificateDto);
  }

  @Override
  public Optional<GiftCertificateDto> updatePrice(
      long id, GiftCertificatePriceDto giftCertificatePriceDto) {
    return Optional.empty();
  }

  private void deletingNonTransmittedTags(long id, Set<Tag> tags) {
    List<Tag> existingTags =
        tagRepository.getEntityListBySpecification(
            new GetAllTagsByGiftCertificatesIdSpecification(id));

    for (Tag existingTag : existingTags) {
      if (!tags.contains(existingTag)) {
        giftCertificateTagRepository.delete(existingTag.getId());
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
