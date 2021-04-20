package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.config.TestDaoConfig;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.specification.tag.GetAllTagsSpecification;
import com.epam.esm.dao.specification.tag.GetTagByIdSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestDaoConfig.class})
@ActiveProfiles("test")
class TagRepositoryImplTest {
  private Tag firstTag;
  private Tag secondTag;
  private Tag thirdTag;
  private Tag fourthTag;
  private Tag tagForManipulations;

  @Autowired private TagRepositoryImpl tagRepository;

  @BeforeEach
  void setUp() {
    firstTag = new Tag(1L, "tag1");

    secondTag = new Tag(2L, "tag2");

    thirdTag = new Tag(3L, "tag3");

    fourthTag = new Tag(4L, "tag4");

    tagForManipulations = new Tag();
    tagForManipulations.setName("tag5");
  }

  //  @Test
  //  void testGetEntityListBySpecification_shouldReturnUnsortedEntityList_whenEntitiesExist() {
  //    // given
  //    List<Tag> expected = Arrays.asList(firstTag, secondTag, thirdTag, fourthTag);
  //    // when
  //    String query = queryFactory.getQuery(null);
  //    List<Tag> actual = tagRepository.getTags(query);
  //    // then
  //    assertEquals(actual, expected);
  //  }
  //
  //  @Test
  //  void testGetEntityListBySpecification_shouldReturnSortedNameAscEntityList_whenEntitiesExist()
  // {
  //    // given
  //    List<Tag> expected = Arrays.asList(firstTag, secondTag, thirdTag, fourthTag);
  //    // when
  //    List<Tag> actual =
  //        tagRepository.getEntityListBySpecification(new GetAllTagsSpecification("name-asc"));
  //    // then
  //    assertEquals(actual, expected);
  //  }
  //
  //  @Test
  //  void testGetEntityListBySpecification_shouldReturnSortedNameDescEntityList_whenEntitiesExist()
  // {
  //    // given
  //    List<Tag> expected = Arrays.asList(fourthTag, thirdTag, secondTag, firstTag);
  //    // when
  //    List<Tag> actual =
  //        tagRepository.getEntityListBySpecification(new GetAllTagsSpecification("name-desc"));
  //    // then
  //    assertEquals(actual, expected);
  //  }
  //
  //  @Test
  //  void testGetEntityBySpecification_shouldReturnTag_whenItExists() {
  //    // given
  //    // when
  //    Optional<Tag> optionalActualTag =
  //        tagRepository.getEntityBySpecification(new GetTagByIdSpecification(1L));
  //    Tag actualTag = optionalActualTag.get();
  //    // then
  //    assertEquals(firstTag, actualTag);
  //  }
  //
  //  @Test
  //  void testGetEntityBySpecification_shouldReturnEmptyOptional_whenTagDoesNotExist() {
  //    // given
  //    // when
  //    Optional<Tag> optionalActualTag =
  //        tagRepository.getEntityBySpecification(new GetTagByIdSpecification(10L));
  //    // then
  //    assertEquals(Optional.empty(), optionalActualTag);
  //  }
  //
  //  @Test
  //  @Transactional
  //  @Rollback
  //  void testDelete_shouldDelete_whenTagIsExists() {
  //    // given
  //    Optional<Tag> optionalSavedTag = tagRepository.save(tagForManipulations);
  //    Long id = optionalSavedTag.get().getId();
  //    // when
  //    int actualResult = tagRepository.delete(id);
  //    // then
  //    assertEquals(1, actualResult);
  //  }
  //
  //  @Test
  //  @Transactional
  //  @Rollback
  //  void testSave_shouldReturnTag_whenUpdateWasSuccessful() {
  //    // given
  //    // when
  //    Optional<Tag> optionalSavedTag = tagRepository.save(tagForManipulations);
  //    Tag actualSavedTag = optionalSavedTag.get();
  //    tagForManipulations.setId(actualSavedTag.getId());
  //    // then
  //    assertEquals(tagForManipulations, actualSavedTag);
  //  }
}
