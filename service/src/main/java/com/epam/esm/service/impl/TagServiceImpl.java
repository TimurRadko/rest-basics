package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.repository.GiftCertificateRepository;
import com.epam.esm.dao.repository.TagRepository;
import com.epam.esm.dao.specification.tag.GetAllTagsAssociatedWithGiftCertificates;
import com.epam.esm.dao.specification.tag.GetAllTagsSpecification;
import com.epam.esm.dao.specification.tag.GetTagByIdSpecification;
import com.epam.esm.dao.specification.tag.GetTagByNameSpecification;
import com.epam.esm.service.TagService;
import com.epam.esm.service.builder.TagBuilder;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.DeletingTagException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.EntityNotValidException;
import com.epam.esm.service.exception.TagAlreadyExistsException;
import com.epam.esm.service.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
  private final TagRepository tagRepository;
  private final TagValidator tagValidator;
  private final GiftCertificateRepository giftCertificateRepository;
  private TagBuilder builder;

  @Autowired
  public TagServiceImpl(
      TagRepository tagRepository,
      TagValidator tagValidator,
      GiftCertificateRepository giftCertificateRepository,
      TagBuilder builder) {
    this.tagRepository = tagRepository;
    this.tagValidator = tagValidator;
    this.giftCertificateRepository = giftCertificateRepository;
    this.builder = builder;
  }

  @Override
  public List<TagDto> getAll(String sort) {
    List<Tag> tags = tagRepository.getEntityListBySpecification(new GetAllTagsSpecification(sort));
    return tags.stream().map((TagDto::new)).collect(Collectors.toList());
  }

  @Override
  public Optional<TagDto> getById(long id) {
    Optional<Tag> optionalTag =
        tagRepository.getEntityBySpecification(new GetTagByIdSpecification(id));
    return optionalTag.map(TagDto::new);
  }

  @Override
  public Optional<TagDto> save(TagDto tagDto) {
    if (!tagValidator.isValid(tagDto)) {
      throw new EntityNotValidException(tagValidator.getErrorMessage());
    }
    Tag tag = builder.build(tagDto);
    Optional<Tag> optionalExistingTag =
        tagRepository.getEntityBySpecification(new GetTagByNameSpecification(tag.getName()));
    if (optionalExistingTag.isEmpty()) {
      Optional<Tag> optionalSavedTag = tagRepository.save(tag);
      return optionalSavedTag.map(TagDto::new);
    } else {
      throw new TagAlreadyExistsException(
          "The tag with this name (" + tagDto.getName() + ") is already in the database");
    }
  }

  @Override
  public int delete(long id) {
    tagRepository
        .getEntityBySpecification(new GetTagByIdSpecification(id))
        .orElseThrow(
            () -> new EntityNotFoundException("Requested resource not found (id = " + id + ")"));

    List<Tag> existingTags =
        tagRepository.getEntityListBySpecification(
            new GetAllTagsAssociatedWithGiftCertificates(id));

    if (!existingTags.isEmpty()) {
      throw new DeletingTagException(
          "The tag with id = " + id + " attached to the Gift Certificate. Deletion denied.");
    }
    return tagRepository.delete(id);
  }
}
