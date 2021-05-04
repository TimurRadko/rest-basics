package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.repository.GiftCertificateRepository;
import com.epam.esm.dao.repository.TagRepository;
import com.epam.esm.dao.specification.gift.GetAllGiftCertificatesAssociatedWithOrders;
import com.epam.esm.dao.specification.gift.GetAllGiftCertificatesSpecification;
import com.epam.esm.dao.specification.gift.GetGiftCertificatesByIdSpecification;
import com.epam.esm.dao.specification.gift.GetGiftCertificatesBySeveralSearchParametersSpecification;
import com.epam.esm.dao.specification.tag.GetAllTagsByGiftCertificatesIdSpecification;
import com.epam.esm.dao.specification.tag.GetTagByIdSpecification;
import com.epam.esm.dao.specification.tag.GetTagByNameSpecification;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.builder.certificate.GiftCertificateBuilder;
import com.epam.esm.service.builder.certificate.GiftCertificateDtoBuilder;
import com.epam.esm.service.builder.tag.TagBuilder;
import com.epam.esm.service.builder.tag.TagDtoBuilder;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.DeletingEntityException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.EntityNotValidMultipleException;
import com.epam.esm.service.exception.PageNotValidException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.GiftCertificateValidator;
import com.epam.esm.service.validator.PageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
  private final GiftCertificateBuilder giftCertificateBuilder;
  private final GiftCertificateDtoBuilder giftCertificateDtoBuilder;
  private final TagBuilder tagBuilder;
  private final TagDtoBuilder tagDtoBuilder;
  private final PageValidator pageValidator;

  @Autowired
  public GiftCertificateServiceImpl(
      GiftCertificateRepository giftCertificateRepository,
      TagRepository tagRepository,
      GiftCertificateValidator giftCertificateValidator,
      GiftCertificateBuilder giftCertificateBuilder,
      GiftCertificateDtoBuilder giftCertificateDtoBuilder,
      TagBuilder tagBuilder,
      TagDtoBuilder tagDtoBuilder,
      PageValidator pageValidator) {
    this.giftCertificateRepository = giftCertificateRepository;
    this.tagRepository = tagRepository;
    this.giftCertificateValidator = giftCertificateValidator;
    this.giftCertificateBuilder = giftCertificateBuilder;
    this.giftCertificateDtoBuilder = giftCertificateDtoBuilder;
    this.tagBuilder = tagBuilder;
    this.tagDtoBuilder = tagDtoBuilder;
    this.pageValidator = pageValidator;
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
          tagRepository.getEntity(new GetTagByNameSpecification(tagDto.getName()));
    } else {
      optionalExistingTag = tagRepository.getEntity(new GetTagByIdSpecification(tagDto.getId()));
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
      Integer page,
      Integer size,
      String name,
      String description,
      List<String> tagNames,
      List<String> sorts) {
    if (!pageValidator.isValid(new PageDto(page, size))) {
      throw new PageNotValidException(pageValidator.getErrorMessage());
    }
    if (name == null && description == null && tagNames == null) {
      return getAll(sorts, page, size);
    }
    return giftCertificateRepository
        .getEntityListWithPagination(
            new GetGiftCertificatesBySeveralSearchParametersSpecification(
                name, description, tagNames, sorts),
            page,
            size)
        .stream()
        .map(giftCertificateDtoBuilder::build)
        .collect(Collectors.toList());
  }

  private List<GiftCertificateDto> getAll(List<String> sort, Integer page, Integer size) {
    return giftCertificateRepository
        .getEntityListWithPagination(new GetAllGiftCertificatesSpecification(sort), page, size)
        .stream()
        .map((giftCertificateDtoBuilder::build))
        .collect(Collectors.toList());
  }

  @Override
  public Optional<GiftCertificateDto> getById(long id) {
    Optional<GiftCertificate> optionalGiftCertificate =
        giftCertificateRepository.getEntity(new GetGiftCertificatesByIdSpecification(id));
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
        .getEntity(new GetGiftCertificatesByIdSpecification(id))
        .orElseThrow(
            () -> new EntityNotFoundException("The Gift Certificate not exists in the DB"));
  }

  @Override
  public Optional<GiftCertificateDto> updateOneField(
      long id, GiftCertificateDto giftCertificateDto) {
    GiftCertificate giftCertificate = getGiftCertificateById(id);
    GiftCertificate newParameterGiftCertificate =
        getNewParameterGiftCertificate(giftCertificate, giftCertificateDto);

    if (!giftCertificateValidator.isValid(
        giftCertificateDtoBuilder.build(newParameterGiftCertificate))) {
      throw new EntityNotValidMultipleException(giftCertificateValidator.getErrorMessage());
    }
    Set<TagDto> tagDtos = giftCertificateDto.getTags();
    tagDtos = saveTags(tagDtos);
    deletingNonTransmittedTags(id, tagDtos);
    giftCertificate = updateGiftCertificate(newParameterGiftCertificate);
    return Optional.of(giftCertificateDtoBuilder.buildWithTagDtos(giftCertificate, tagDtos));
  }

  private GiftCertificate getNewParameterGiftCertificate(
      GiftCertificate giftCertificate, GiftCertificateDto giftCertificateDto) {
    setNewName(giftCertificate, giftCertificateDto);
    setNewPrice(giftCertificate, giftCertificateDto);
    setNewDuration(giftCertificate, giftCertificateDto);
    setNewDescription(giftCertificate, giftCertificateDto);
    return giftCertificate;
  }

  private void setNewName(GiftCertificate giftCertificate, GiftCertificateDto giftCertificateDto) {
    String name = giftCertificateDto.getName();
    if (name != null) {
      giftCertificate.setName(name);
    }
  }

  private void setNewPrice(GiftCertificate giftCertificate, GiftCertificateDto giftCertificateDto) {
    BigDecimal price = giftCertificateDto.getPrice();
    if (price != null) {
      giftCertificate.setPrice(price);
    }
  }

  private void setNewDuration(
      GiftCertificate giftCertificate, GiftCertificateDto giftCertificateDto) {
    Integer duration = giftCertificateDto.getDuration();
    if (duration != null) {
      giftCertificate.setDuration(duration);
    }
  }

  private void setNewDescription(
      GiftCertificate giftCertificate, GiftCertificateDto giftCertificateDto) {
    String description = giftCertificateDto.getDescription();
    if (description != null) {
      giftCertificate.setDescription(description);
    }
  }

  private GiftCertificate getGiftCertificateById(Long id) {
    return giftCertificateRepository
        .getEntity(new GetGiftCertificatesByIdSpecification(id))
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
        tagRepository.getEntityList(new GetAllTagsByGiftCertificatesIdSpecification(id));
    Set<Tag> tags = tagDtos.stream().map(tagBuilder::build).collect(Collectors.toSet());
    for (Tag existingTag : existingTags) {
      if (!tags.contains(existingTag)) {
        tagRepository.delete(existingTag.getId());
      }
    }
  }

  @Override
  @Transactional
  public int delete(long id) {
    giftCertificateRepository
        .getEntity(new GetGiftCertificatesByIdSpecification(id))
        .orElseThrow(
            () -> new EntityNotFoundException("Requested resource not found (id = " + id + ")"));

    List<GiftCertificate> existingGiftCertificate =
        giftCertificateRepository.getEntityList(new GetAllGiftCertificatesAssociatedWithOrders(id));

    if (!existingGiftCertificate.isEmpty()) {
      throw new DeletingEntityException(
          "The Gift Certificate with id = " + id + " attached to the Order. Deletion denied.");
    }
    return giftCertificateRepository.delete(id);
  }
}
