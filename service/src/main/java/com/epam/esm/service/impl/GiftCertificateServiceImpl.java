package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.GiftCertificateTag;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.repository.GiftCertificateTagRepository;
import com.epam.esm.dao.repository.TagRepository;
import com.epam.esm.dao.specification.gift.*;
import com.epam.esm.dao.specification.gifttag.GetGiftCertificateTagByGiftCertificateIdSpecification;
import com.epam.esm.dao.specification.tag.GetTagByNameSpecification;
import com.epam.esm.dao.specification.tag.GetAllTagsByGiftCertificatesIdSpecification;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.dao.repository.GiftCertificateRepository;
import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.builder.GiftCertificateBuilder;
import com.epam.esm.service.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
  private final GiftCertificateRepository giftCertificateRepository;
  private final TagRepository tagRepository;
  private final GiftCertificateTagRepository giftCertificateTagRepository;
  private final GiftCertificateValidator giftCertificateValidator;
  private final GiftCertificateBuilder builder;
  private static final String CERTIFICATE_NOT_EXISTS_IN_THE_DB =
      "The Gift Certificate not exists in the DB";

  @Autowired
  public GiftCertificateServiceImpl(
      GiftCertificateRepository giftCertificateRepository,
      TagRepository tagRepository,
      GiftCertificateTagRepository giftCertificateTagRepository,
      GiftCertificateValidator giftCertificateValidator,
      GiftCertificateBuilder builder) {
    this.giftCertificateRepository = giftCertificateRepository;
    this.tagRepository = tagRepository;
    this.giftCertificateTagRepository = giftCertificateTagRepository;
    this.giftCertificateValidator = giftCertificateValidator;
    this.builder = builder;
  }

  @Override
  @Transactional
  public Optional<GiftCertificateDto> save(GiftCertificateDto giftCertificateDto) {
    if (giftCertificateValidator.validate(giftCertificateDto)) {
      GiftCertificate giftCertificate = builder.buildFromDto(giftCertificateDto);
      giftCertificate =
          giftCertificateRepository
              .save(giftCertificate)
              .orElseThrow(() -> new ServiceException("The Gift Certificate wasn't saved"));

      Set<Tag> tags = giftCertificateDto.getTags();
      tags = saveTags(tags, giftCertificate.getId());

      giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
      return Optional.of(giftCertificateDto);
    } else {
      throw new ServiceException(giftCertificateValidator.getErrorMessage());
    }
  }

  private Set<Tag> saveTags(Set<Tag> tags, long giftCertificateId) {
    Set<Tag> createdTags = new HashSet<>();
    if (tags != null) {
      tags.forEach((tag -> createdTags.add(saveTagIfNotExist(tag, giftCertificateId))));
    }
    return createdTags;
  }

  private Tag saveTagIfNotExist(Tag tag, long giftCertificateId) {
    Optional<Tag> optionalExistingTag =
        tagRepository.getEntityBySpecification(new GetTagByNameSpecification(tag.getName()));
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
    return tag;
  }

  private void saveGiftCertificateTag(long giftCertificateId, Tag tag) {
    GiftCertificateTag giftCertificateTag = new GiftCertificateTag();
    giftCertificateTag.setTagId(tag.getId());
    giftCertificateTag.setGiftCertificateId(giftCertificateId);
    giftCertificateTagRepository.save(giftCertificateTag);
  }

  @Override
  public List<GiftCertificateDto> getAll(String sort) {
    List<GiftCertificate> giftCertificates =
        giftCertificateRepository.getEntityListBySpecification(
            new GetAllGiftCertificatesSpecification(sort));
    return giftCertificates.stream()
        .map((this::createGiftCertificateDto))
        .collect(Collectors.toList());
  }

  @Override
  public List<GiftCertificateDto> getGiftCertificatesByNameOrDescriptionPart(
      String name, String description, String sort) {

    List<GiftCertificate> giftCertificates = new ArrayList<>();
    if (name != null && name.trim().length() != 0) {
      giftCertificates =
          giftCertificateRepository.getEntityListBySpecification(
              new GetGiftCertificatesByNamePartSpecification(name, sort));
    }
    if (description != null && description.trim().length() != 0) {
      giftCertificates =
          giftCertificateRepository.getEntityListBySpecification(
              new GetGiftCertificatesByDescriptionPartSpecification(description, sort));
    }
    return giftCertificates.stream()
        .map((this::createGiftCertificateDto))
        .collect(Collectors.toList());
  }

  @Override
  public List<GiftCertificateDto> getGiftCertificateListByTagName(
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
            new GetGiftCertificateByIdSpecification(id));
    if (optionalGiftCertificate.isPresent()) {
      GiftCertificate giftCertificate = optionalGiftCertificate.get();
      return Optional.of(createGiftCertificateDto(giftCertificate));
    }
    return Optional.empty();
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
    if (giftCertificateValidator.validate(giftCertificateDto)) {
      GiftCertificate giftCertificate = builder.buildFromDto(giftCertificateDto);
      GiftCertificate existingGiftCertificate =
          giftCertificateRepository
              .getEntityBySpecification(new GetGiftCertificateByIdSpecification(id))
              .orElseThrow(() -> new ServiceException(CERTIFICATE_NOT_EXISTS_IN_THE_DB));

      giftCertificate =
          builder.buildNewParameterGiftCertificate(existingGiftCertificate, giftCertificate);
      GiftCertificate newGiftCertification =
          giftCertificateRepository
              .update(giftCertificate)
              .orElseThrow(() -> new ServiceException(CERTIFICATE_NOT_EXISTS_IN_THE_DB));

      Set<Tag> tags = giftCertificateDto.getTags();
      tags = saveTags(tags, newGiftCertification.getId());

      List<Tag> existingTag =
          tagRepository.getEntityListBySpecification(
              new GetAllTagsByGiftCertificatesIdSpecification(id));
      tags.addAll(existingTag);

      giftCertificateDto = new GiftCertificateDto(newGiftCertification, tags);
      return Optional.of(giftCertificateDto);
    } else {
      throw new ServiceException(giftCertificateValidator.getErrorMessage());
    }
  }

  @Override
  public int delete(long id) {
    return giftCertificateRepository.delete(id);
  }
}
