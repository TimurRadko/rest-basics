package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.repository.TagRepository;
import com.epam.esm.service.builder.TagBuilder;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.TagValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
  @Mock private TagRepository tagRepository;
  @Mock private TagValidator tagValidator;
  @Mock private TagBuilder builder;
  @InjectMocks private TagServiceImpl tagService;

  private Tag firstTestTag;
  private TagDto firstTestTagDto;
  private List<Tag> expectedTags;
  private List<TagDto> expectedTagDtos;
  private static final String NULL_SORTING = null;
  private static final long ID_FOR_MANIPULATIONS = 1L;

  @BeforeEach
  void setUp() {
    firstTestTag = new Tag(1L, "tag1");
    firstTestTagDto = new TagDto(firstTestTag);
    Tag secondTestTag = new Tag(2L, "tag2");
    expectedTags = Arrays.asList(firstTestTag, secondTestTag);
    expectedTagDtos = expectedTags.stream().map(TagDto::new).collect(Collectors.toList());
  }

  @Test
  void testGetAll_shouldReturnTagList_whenTagsExist() {
    // given
    when(tagRepository.getEntityListBySpecification(any())).thenReturn(expectedTags);
    // when
    List<TagDto> actualTagDtos = tagService.getAll(NULL_SORTING);
    // then
    assertEquals(expectedTagDtos, actualTagDtos);
  }

  @Test
  void testGetById_shouldReturnRightTag_whenItExists() {
    // given
    when(tagRepository.getEntityBySpecification(any())).thenReturn(Optional.of(firstTestTag));
    // when
    TagDto actualTagDto = tagService.getById(ID_FOR_MANIPULATIONS).orElse(new TagDto());
    // then
    assertEquals(firstTestTagDto, actualTagDto);
  }

  @Test
  void testSave_shouldReturnSavedTag_whenParametersIsValid() {
    // given
    when(tagRepository.getEntityBySpecification(any())).thenReturn(Optional.empty());
    when(tagRepository.save(firstTestTag)).thenReturn(Optional.of(firstTestTag));
    when(tagValidator.isValid(firstTestTagDto)).thenReturn(true);
    when(builder.buildFromDto(firstTestTagDto)).thenReturn(firstTestTag);
    // when
    Optional<TagDto> optionalActualTagDto = tagService.save(firstTestTagDto);
    // then
    assertEquals(firstTestTagDto, optionalActualTagDto.get());
  }

  @Test
  void testSave_shouldThrowServiceException_whenTagExistsInDatabase() {
    // given
    when(tagRepository.getEntityBySpecification(any())).thenReturn(Optional.of(firstTestTag));
    when(tagValidator.isValid(firstTestTagDto)).thenReturn(true);
    when(builder.buildFromDto(firstTestTagDto)).thenReturn(firstTestTag);
    // when
    // then
    assertThrows(ServiceException.class, () -> tagService.save(firstTestTagDto));
  }

  @Test
  void testSave_shouldThrowServiceException_whenTagIsInvalid() {
    // given
    // when
    when(tagValidator.isValid(any())).thenReturn(false);
    // then
    assertThrows(ServiceException.class, () -> tagService.save(firstTestTagDto));
  }

  @Test
  void testDelete_ShouldDeleteTag_whenItExists() {
    // given
    int expectedResult = 1;
    when(tagRepository.delete(ID_FOR_MANIPULATIONS)).thenReturn(expectedResult);
    // when
    int actualResult = tagService.delete(ID_FOR_MANIPULATIONS);
    // then
    assertEquals(expectedResult, actualResult);
  }
}
