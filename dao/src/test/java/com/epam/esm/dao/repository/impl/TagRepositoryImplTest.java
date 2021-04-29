package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.config.TestDaoConfig;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.specification.Specification;
import com.epam.esm.dao.specification.tag.GetAllTagsSpecification;
import com.epam.esm.dao.specification.tag.GetTagByIdSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestDaoConfig.class})
@ActiveProfiles("test")
@SpringBootTest
class TagRepositoryImplTest {
  private Tag firstTag;
  private Tag secondTag;
  private Tag thirdTag;
  private Tag fourthTag;
  private CriteriaBuilder builder;

  @Autowired private EntityManager entityManager;

  @BeforeEach
  void setUp() {
    firstTag = new Tag(1L, "tag1");
    secondTag = new Tag(2L, "tag2");
    thirdTag = new Tag(3L, "tag3");
    fourthTag = new Tag(4L, "tag4");
    builder = entityManager.getCriteriaBuilder();
  }

  @Test
  void testGetEntityListBySpecification_shouldReturnUnsortedEntityList_whenEntitiesExist() {
    // given
    List<Tag> expected = Arrays.asList(firstTag, secondTag, thirdTag, fourthTag);
    Specification<Tag> specification = new GetAllTagsSpecification(null);
    CriteriaQuery<Tag> criteriaQuery = specification.getCriteriaQuery(builder);
    // when
    List<Tag> actual = entityManager.createQuery(criteriaQuery).getResultList();
    // then
    assertEquals(expected, actual);
  }

  @Test
  void testGetEntityListBySpecification_shouldReturnSortedNameAscEntityList_whenEntitiesExist() {
    // given
    List<Tag> expected = Arrays.asList(firstTag, secondTag, thirdTag, fourthTag);
    Specification<Tag> specification = new GetAllTagsSpecification("name-asc");
    CriteriaQuery<Tag> criteriaQuery = specification.getCriteriaQuery(builder);
    // when
    List<Tag> actual = entityManager.createQuery(criteriaQuery).getResultList();
    // then
    assertEquals(expected, actual);
  }

  @Test
  void testGetEntityListBySpecification_shouldReturnSortedNameDescEntityList_whenEntitiesExist() {
    // given
    List<Tag> expected = Arrays.asList(fourthTag, thirdTag, secondTag, firstTag);
    Specification<Tag> specification = new GetAllTagsSpecification("name-desc");
    CriteriaQuery<Tag> criteriaQuery = specification.getCriteriaQuery(builder);
    // when
    List<Tag> actual = entityManager.createQuery(criteriaQuery).getResultList();
    // then
    assertEquals(expected, actual);
  }

  @Test
  void testGetEntityBySpecification_shouldReturnTag_whenItExists() {
    // given
    Specification<Tag> specification = new GetTagByIdSpecification(1L);
    CriteriaQuery<Tag> criteriaQuery = specification.getCriteriaQuery(builder);
    // when
    Tag actualTag = entityManager.createQuery(criteriaQuery).getSingleResult();
    // then
    assertEquals(actualTag, firstTag);
  }

  @Test
  void testGetEntityBySpecification_shouldThrowNoResultException_whenTagDoesNotExist() {
    // given
    Specification<Tag> specification = new GetTagByIdSpecification(10L);
    CriteriaQuery<Tag> criteriaQuery = specification.getCriteriaQuery(builder);
    // when
    // then
    assertThrows(
        NoResultException.class, () -> entityManager.createQuery(criteriaQuery).getSingleResult());
  }
}
