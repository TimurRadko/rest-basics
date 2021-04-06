package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.repository.TagRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.TagValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

  @Mock private TagRepository tagRepository;
  @InjectMocks private TagServiceImpl tagService;
  @Mock private TagValidator tagValidator;

  private Tag firstTestEntity;
  private List<Tag> expectedTags;
  private static final String NULL_SORTING = null;
  private static final long ID_FOR_MANIPULATIONS = 1L;

  @BeforeEach
  void setUp() {
    firstTestEntity = new Tag(1L, "tag1");
    Tag secondTestEntity = new Tag(2L, "tag2");
    expectedTags = Arrays.asList(firstTestEntity, secondTestEntity);
  }

  @Test
  void testGetAll_shouldReturnTagList_whenTagsExist() {
    // given
    when(tagRepository.getEntityListBySpecification(any())).thenReturn(expectedTags);
    // when
    List<Tag> actualTags = tagService.getAll(NULL_SORTING);
    // then
    assertEquals(expectedTags, actualTags);
  }

  @Test
  void testGetById_shouldReturnRightTag_whenItExists() {
    // given
    when(tagRepository.getEntityBySpecification(any())).thenReturn(Optional.of(firstTestEntity));
    // when
    Tag actualTag = tagService.getById(ID_FOR_MANIPULATIONS).orElse(new Tag());
    // then
    assertEquals(firstTestEntity, actualTag);
  }

  @Test
  void testSave_shouldReturnWasSavedTag_whenParametersIsValid() {
    // given
    when(tagRepository.getEntityBySpecification(any())).thenReturn(Optional.of(firstTestEntity));
    when(tagRepository.save(firstTestEntity)).thenReturn(Optional.of(firstTestEntity));
    when(tagValidator.validate(firstTestEntity)).thenReturn(true);
    // when
    Optional<Tag> optionalActualTag = tagService.save(firstTestEntity);
    // then
    assertEquals(firstTestEntity, optionalActualTag.get());
  }

  @Test
  void testSave_shouldThrowServiceException_whenTagIsInvalid() {
    // given
    Mockito.lenient()
        .when(tagRepository.save(firstTestEntity))
        .thenReturn(Optional.of(firstTestEntity));
    // when
    when(tagValidator.validate(any())).thenReturn(false);
    // then
    assertThrows(ServiceException.class, () -> tagService.save(firstTestEntity));
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
