package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.config.TestDaoConfig;
import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.specification.Specification;
import com.epam.esm.dao.specification.gift.GetAllGiftCertificatesSpecification;
import com.epam.esm.dao.specification.gift.GetGiftCertificatesByIdSpecification;
import com.epam.esm.dao.specification.gift.GetGiftCertificatesBySeveralSearchParametersSpecification;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestDaoConfig.class})
@ActiveProfiles("test")
@SpringBootTest
class GiftCertificateRepositoryImplTest {
  private GiftCertificate firstGiftCertificate;
  private GiftCertificate secondGiftCertificate;
  private GiftCertificate thirdGiftCertificate;
  private GiftCertificate fourthGiftCertificate;
  private CriteriaBuilder builder;

  private static final int FIRST_PAGE = 0;
  private static final int SIZE = 4;

  @Autowired private EntityManager entityManager;

  @BeforeEach
  void setUp() {
    firstGiftCertificate =
        new GiftCertificate(
            1L,
            "BBB",
            "The first certificate",
            BigDecimal.valueOf(55.0),
            14,
            LocalDateTime.parse("2021-03-25T00:00:00"),
            LocalDateTime.parse("2020-10-05T00:00:00"));

    secondGiftCertificate =
        new GiftCertificate(
            2L,
            "AAA",
            "The second certificate",
            BigDecimal.valueOf(35.0),
            18,
            LocalDateTime.parse("2021-07-07T00:00:00"),
            LocalDateTime.parse("2020-11-07T00:00:00"));

    thirdGiftCertificate =
        new GiftCertificate(
            3L,
            "DDD",
            "The third certificate",
            BigDecimal.valueOf(50.0),
            11,
            LocalDateTime.parse("2020-05-09T00:00:00"),
            LocalDateTime.parse("2020-10-09T00:00:00"));

    fourthGiftCertificate =
        new GiftCertificate(
            4L,
            "CCC",
            "The fourth certificate",
            BigDecimal.valueOf(14.0),
            12,
            LocalDateTime.parse("2020-05-07T00:00:00"),
            LocalDateTime.parse("2020-10-07T00:00:00"));

    builder = entityManager.getCriteriaBuilder();
  }

  @Test
  void testGetEntityListBySpecification_shouldReturnUnsortedEntityList_whenEntitiesExist() {
    // given
    List<GiftCertificate> expected =
        Arrays.asList(
            firstGiftCertificate,
            secondGiftCertificate,
            thirdGiftCertificate,
            fourthGiftCertificate);
    Specification<GiftCertificate> specification = new GetAllGiftCertificatesSpecification(null);
    CriteriaQuery<GiftCertificate> criteriaQuery = specification.getCriteriaQuery(builder);
    // when
    List<GiftCertificate> actual =
        entityManager
            .createQuery(criteriaQuery)
            .setFirstResult(FIRST_PAGE)
            .setMaxResults(SIZE)
            .getResultList();
    // then
    assertEquals(expected, actual);
  }

  @Test
  void
      testGetEntityListBySpecification_shouldReturnSortedByNameDescEntityList_whenEntitiesIsExist() {
    // given
    List<GiftCertificate> expected =
        Arrays.asList(
            thirdGiftCertificate,
            fourthGiftCertificate,
            firstGiftCertificate,
            secondGiftCertificate);
    // when
    Specification<GiftCertificate> specification =
        new GetAllGiftCertificatesSpecification(List.of("name-desc"));
    CriteriaQuery<GiftCertificate> criteriaQuery = specification.getCriteriaQuery(builder);
    // when
    List<GiftCertificate> actual =
        entityManager
            .createQuery(criteriaQuery)
            .setFirstResult(FIRST_PAGE)
            .setMaxResults(SIZE)
            .getResultList();
    // then
    assertEquals(expected, actual);
  }

  @Test
  void testGetEntityListBySpecification_shouldReturnSortedByNameAscEntityList_whenEntitiesExist() {
    // given
    List<GiftCertificate> expected =
        Arrays.asList(
            secondGiftCertificate,
            firstGiftCertificate,
            fourthGiftCertificate,
            thirdGiftCertificate);
    Specification<GiftCertificate> specification =
        new GetAllGiftCertificatesSpecification(List.of("name-asc"));
    CriteriaQuery<GiftCertificate> criteriaQuery = specification.getCriteriaQuery(builder);
    // when
    List<GiftCertificate> actual =
        entityManager
            .createQuery(criteriaQuery)
            .setFirstResult(FIRST_PAGE)
            .setMaxResults(SIZE)
            .getResultList();
    // then
    assertEquals(expected, actual);
  }

  @Test
  void
      testGetEntityListBySpecification_shouldReturnSortedByCreateDateAscEntityList_whenEntitiesExist() {
    // given
    List<GiftCertificate> expected =
        Arrays.asList(
            fourthGiftCertificate,
            thirdGiftCertificate,
            firstGiftCertificate,
            secondGiftCertificate);
    Specification<GiftCertificate> specification =
        new GetAllGiftCertificatesSpecification(List.of("create-date-asc"));
    CriteriaQuery<GiftCertificate> criteriaQuery = specification.getCriteriaQuery(builder);
    // when
    List<GiftCertificate> actual =
        entityManager
            .createQuery(criteriaQuery)
            .setFirstResult(FIRST_PAGE)
            .setMaxResults(SIZE)
            .getResultList();
    // then
    assertEquals(actual, expected);
  }

  @Test
  void
      testGetEntityListBySpecification_shouldReturnSortedByCreateDateDescEntityList_whenEntitiesExist() {
    // given
    List<GiftCertificate> expected =
        Arrays.asList(
            secondGiftCertificate,
            firstGiftCertificate,
            thirdGiftCertificate,
            fourthGiftCertificate);
    Specification<GiftCertificate> specification =
        new GetAllGiftCertificatesSpecification(List.of("create-date-desc"));
    CriteriaQuery<GiftCertificate> criteriaQuery = specification.getCriteriaQuery(builder);
    // when
    List<GiftCertificate> actual =
        entityManager
            .createQuery(criteriaQuery)
            .setFirstResult(FIRST_PAGE)
            .setMaxResults(SIZE)
            .getResultList();
    // then
    assertEquals(actual, expected);
  }

  @Test
  void testGetEntityListBySpecification_shouldReturnEntityListByNamePart_whenEntitiesExist() {
    // given
    List<GiftCertificate> expected = Collections.singletonList(secondGiftCertificate);
    Specification<GiftCertificate> specification =
        new GetGiftCertificatesBySeveralSearchParametersSpecification("A", null, null, null);
    CriteriaQuery<GiftCertificate> criteriaQuery = specification.getCriteriaQuery(builder);
    // when
    List<GiftCertificate> actual =
        entityManager
            .createQuery(criteriaQuery)
            .setFirstResult(FIRST_PAGE)
            .setMaxResults(SIZE)
            .getResultList();
    // then
    assertEquals(expected, actual);
  }

  @Test
  void
      testGetEntityListBySpecification_shouldReturnEntityListByDescriptionPart_whenEntitiesExist() {
    // given
    List<GiftCertificate> expected = Collections.singletonList(secondGiftCertificate);
    Specification<GiftCertificate> specification =
        new GetGiftCertificatesBySeveralSearchParametersSpecification(null, "sec", null, null);
    CriteriaQuery<GiftCertificate> criteriaQuery = specification.getCriteriaQuery(builder);
    // when
    List<GiftCertificate> actual =
        entityManager
            .createQuery(criteriaQuery)
            .setFirstResult(FIRST_PAGE)
            .setMaxResults(SIZE)
            .getResultList();
    // then
    assertEquals(expected, actual);
  }

  @Test
  void testGetEntityBySpecification_shouldReturnGift_whenItExists() {
    // given
    Specification<GiftCertificate> specification = new GetGiftCertificatesByIdSpecification(2L);
    CriteriaQuery<GiftCertificate> criteriaQuery = specification.getCriteriaQuery(builder);
    // when
    GiftCertificate actualGiftCertificate =
        entityManager.createQuery(criteriaQuery).getSingleResult();
    // then
    assertEquals(secondGiftCertificate, actualGiftCertificate);
  }

  @Test
  void testGetEntityBySpecification_shouldThrowNoResultException_whenItDoesNotExist() {
    // given
    Specification<GiftCertificate> specification = new GetGiftCertificatesByIdSpecification(10L);
    CriteriaQuery<GiftCertificate> criteriaQuery = specification.getCriteriaQuery(builder);
    // when
    // then
    assertThrows(
        NoResultException.class, () -> entityManager.createQuery(criteriaQuery).getSingleResult());
  }
}
