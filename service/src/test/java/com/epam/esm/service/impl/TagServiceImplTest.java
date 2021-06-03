package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.repository.TagRepository;
import com.epam.esm.service.builder.tag.TagBuilder;
import com.epam.esm.service.builder.tag.TagDtoBuilder;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.tag.DeletingTagException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.EntityNotValidException;
import com.epam.esm.service.exception.PageNotValidException;
import com.epam.esm.service.exception.tag.TagAlreadyExistsException;
import com.epam.esm.service.locale.LocaleTranslator;
import com.epam.esm.service.validator.PageValidator;
import com.epam.esm.service.validator.TagValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
  @Mock private TagRepository tagRepository;
  @Mock private TagValidator tagValidator;
  @Mock private PageValidator pageValidator;
  @Mock private TagBuilder tagBuilder;
  @Mock private TagDtoBuilder tagDtoBuilder;
  @Mock private LocaleTranslator localeTranslator;
  @InjectMocks private TagServiceImpl tagService;

  private Tag firstTestTag;
  private TagDto firstTestTagDto;
  private List<Tag> expectedTags;
  private List<TagDto> expectedTagDtos;
  private static final String NULL_SORTING = null;
  private static final long ID_FOR_MANIPULATIONS = 1L;
  private static final long FIRST_TAG_ID = 1L;
  private static final String FIRST_TAG_NAME = "tag1";
  private static final int DEFAULT_PAGE_PARAMETER = 1;
  private static final int DEFAULT_SIZE_PARAMETER = 1;

  @BeforeEach
  void setUp() {
    firstTestTag = new Tag(FIRST_TAG_ID, FIRST_TAG_NAME);
    firstTestTagDto = new TagDto(FIRST_TAG_ID, FIRST_TAG_NAME);
    expectedTags = Collections.singletonList(firstTestTag);
    expectedTagDtos = Collections.singletonList(firstTestTagDto);
  }

  @Test
  void testGetAll_shouldReturnTagList_whenTagsExist() {
    // given
    when(pageValidator.isValid(any())).thenReturn(true);
    when(tagRepository.getEntityListWithPagination(any(), anyInt(), anyInt()))
        .thenReturn(expectedTags);
    when(tagDtoBuilder.build(any())).thenReturn(firstTestTagDto);
    // when
    List<TagDto> actualTagDtos =
        tagService.getAll(DEFAULT_PAGE_PARAMETER, DEFAULT_SIZE_PARAMETER, NULL_SORTING);
    // then
    assertEquals(expectedTagDtos, actualTagDtos);
  }

  @Test
  void testGetAll_shouldThrowPageNotValidException_whenPageParametersNotValid() {
    // given
    when(pageValidator.isValid(any())).thenReturn(false);
    // when
    // then
    assertThrows(
        PageNotValidException.class,
        () -> tagService.getAll(DEFAULT_PAGE_PARAMETER, DEFAULT_SIZE_PARAMETER, NULL_SORTING));
  }

  @Test
  void testGetById_shouldReturnRightTag_whenItExists() {
    // given
    when(tagRepository.getEntity(any())).thenReturn(Optional.of(firstTestTag));
    when(tagDtoBuilder.build(any())).thenReturn(firstTestTagDto);
    // when
    TagDto actualTagDto = tagService.getById(ID_FOR_MANIPULATIONS).orElse(new TagDto());
    // then
    assertEquals(firstTestTagDto, actualTagDto);
  }

  @Test
  void testSave_shouldReturnSavedTag_whenParametersIsValid() {
    // given
    when(tagRepository.getEntity(any())).thenReturn(Optional.empty());
    when(tagRepository.save(firstTestTag)).thenReturn(Optional.of(firstTestTag));
    when(tagValidator.isValid(firstTestTagDto)).thenReturn(true);
    when(tagBuilder.build(firstTestTagDto)).thenReturn(firstTestTag);
    when(tagDtoBuilder.build(firstTestTag)).thenReturn(firstTestTagDto);
    // when
    Optional<TagDto> optionalActualTagDto = tagService.save(firstTestTagDto);
    // then
    assertEquals(firstTestTagDto, optionalActualTagDto.get());
  }

  @Test
  void testSave_shouldThrowEntityNotValidException_whenTagNotValid() {
    // given
    when(tagValidator.isValid(firstTestTagDto)).thenReturn(false);
    // when
    // then
    assertThrows(EntityNotValidException.class, () -> tagService.save(firstTestTagDto));
  }

  @Test
  void testSave_shouldThrowTagAlreadyExistsException_whenTagExistsInDatabase() {
    // given
    when(tagRepository.getEntity(any())).thenReturn(Optional.of(firstTestTag));
    when(tagValidator.isValid(firstTestTagDto)).thenReturn(true);
    when(tagBuilder.build(firstTestTagDto)).thenReturn(firstTestTag);
    // when
    when(localeTranslator.toLocale(any()))
        .thenReturn(
            String.format(
                "The Tag with this name (%s) is already in the database.", firstTestTag.getId()));
    // then
    assertThrows(TagAlreadyExistsException.class, () -> tagService.save(firstTestTagDto));
  }

  @Test
  void testDelete_ShouldThrowDeletingTagException_whenItExists() {
    // given
    when(tagRepository.getEntity(any())).thenReturn(Optional.of(firstTestTag));
    when(tagRepository.getEntityList(any())).thenReturn(Collections.singletonList(firstTestTag));
    // when
    when(localeTranslator.toLocale(any()))
        .thenReturn(
            String.format(
                "The Tag with id = %s attached to the Gift Certificate. Deletion denied.",
                firstTestTag.getId()));
    // then
    assertThrows(DeletingTagException.class, () -> tagService.delete(ID_FOR_MANIPULATIONS));
  }

  @Test
  void testDelete_shouldDeleteTag_whenItExistsAndNotAttachedToGiftCertificate() {
    // given
    int expectedResult = 1;
    when(tagRepository.delete(ID_FOR_MANIPULATIONS)).thenReturn(expectedResult);
    when(tagRepository.getEntity(any())).thenReturn(Optional.of(firstTestTag));
    when(tagRepository.getEntityList(any())).thenReturn(Collections.emptyList());
    // when
    int actualResult = tagService.delete(ID_FOR_MANIPULATIONS);
    // then
    assertEquals(expectedResult, actualResult);
  }

  @Test
  void testDelete_shouldThrowEntityNotFoundException_whenItNotExistsInDatabase() {
    // given
    when(tagRepository.getEntity(any())).thenReturn(Optional.empty());
    // when
    when(localeTranslator.toLocale(any()))
        .thenReturn(
            String.format("Requested resource with id = %s not found.", firstTestTag.getId()));
    // then
    assertThrows(EntityNotFoundException.class, () -> tagService.delete(ID_FOR_MANIPULATIONS));
  }
}
