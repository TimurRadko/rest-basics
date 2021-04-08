package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.repository.TagRepository;
import com.epam.esm.dao.specification.tag.GetAllTagsSpecification;
import com.epam.esm.dao.specification.tag.GetTagByIdSpecification;
import com.epam.esm.dao.specification.tag.GetTagByNameSpecification;
import com.epam.esm.service.TagService;
import com.epam.esm.service.builder.TagBuilder;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
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
  private TagBuilder builder;

  @Autowired
  public TagServiceImpl(
      TagRepository tagRepository, TagValidator tagValidator, TagBuilder builder) {
    this.tagRepository = tagRepository;
    this.tagValidator = tagValidator;
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
    if (tagValidator.validate(tagDto)) {
      Tag tag = builder.buildFromDto(tagDto);
      String name = tag.getName();
      Optional<Tag> optionalExistingTag =
          tagRepository.getEntityBySpecification(new GetTagByNameSpecification(name));
      if (optionalExistingTag.isEmpty()) {
        Optional<Tag> optionalSavedTag = tagRepository.save(tag);
        return optionalSavedTag.map(TagDto::new);
      } else {
        throw new ServiceException(
            "The tag with this name (" + name + ") is already in the database");
      }
    } else {
      throw new ServiceException(tagValidator.getErrorMessage());
    }
  }

  @Override
  public int delete(long id) {
    return tagRepository.delete(id);
  }
}
