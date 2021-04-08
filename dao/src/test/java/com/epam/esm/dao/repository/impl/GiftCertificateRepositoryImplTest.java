package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.config.TestDaoConfig;
import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.specification.gift.GetAllGiftCertificatesSpecification;
import com.epam.esm.dao.specification.gift.GetGiftCertificateByIdSpecification;
import com.epam.esm.dao.specification.gift.GetGiftCertificatesByDescriptionPartSpecification;
import com.epam.esm.dao.specification.gift.GetGiftCertificatesByNamePartSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestDaoConfig.class})
@ActiveProfiles("test")
class GiftCertificateRepositoryImplTest {
  private GiftCertificate firstGiftCertificate;
  private GiftCertificate secondGiftCertificate;
  private GiftCertificate thirdGiftCertificate;
  private GiftCertificate fourthGiftCertificate;

  @Autowired private GiftCertificateRepositoryImpl giftCertificateRepository;

  @BeforeEach
  void setUp() {
    firstGiftCertificate =
        new GiftCertificate(
            1L,
            "The first",
            "The first certificate",
            BigDecimal.valueOf(55.0),
            14,
            LocalDateTime.parse("2021-03-25T00:00:00"),
            LocalDateTime.parse("2020-10-05T00:00:00"));

    secondGiftCertificate =
        new GiftCertificate(
            2L,
            "The second",
            "The second certificate",
            BigDecimal.valueOf(35.0),
            18,
            LocalDateTime.parse("2021-07-07T00:00:00"),
            LocalDateTime.parse("2020-11-07T00:00:00"));

    thirdGiftCertificate =
        new GiftCertificate(
            3L,
            "The third",
            "The third certificate",
            BigDecimal.valueOf(50.0),
            11,
            LocalDateTime.parse("2020-05-09T00:00:00"),
            LocalDateTime.parse("2020-10-09T00:00:00"));

    fourthGiftCertificate =
        new GiftCertificate(
            4L,
            "The fourth",
            "The fourth certificate",
            BigDecimal.valueOf(14.0),
            12,
            LocalDateTime.parse("2020-05-07T00:00:00"),
            LocalDateTime.parse("2020-10-07T00:00:00"));
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
    // when
    List<GiftCertificate> actual =
        giftCertificateRepository.getEntityListBySpecification(
            new GetAllGiftCertificatesSpecification(null));
    // then
    assertEquals(actual, expected);
  }

  @Test
  void
      testGetEntityListBySpecification_shouldReturnSortedByNameDescEntityList_whenEntitiesIsExist() {
    // given
    List<GiftCertificate> expected =
        Arrays.asList(
            thirdGiftCertificate,
            secondGiftCertificate,
            fourthGiftCertificate,
            firstGiftCertificate);
    List<GiftCertificate> actual =
        giftCertificateRepository.getEntityListBySpecification(
            new GetAllGiftCertificatesSpecification("name-desc"));
    // then
    assertEquals(actual, expected);
  }

  @Test
  void testGetEntityListBySpecification_shouldReturnSortedByNameAscEntityList_whenEntitiesExist() {
    // given
    List<GiftCertificate> expected =
        Arrays.asList(
            firstGiftCertificate,
            fourthGiftCertificate,
            secondGiftCertificate,
            thirdGiftCertificate);
    List<GiftCertificate> actual =
        giftCertificateRepository.getEntityListBySpecification(
            new GetAllGiftCertificatesSpecification("name-asc"));
    // then
    assertEquals(actual, expected);
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
    List<GiftCertificate> actual =
        giftCertificateRepository.getEntityListBySpecification(
            new GetAllGiftCertificatesSpecification("create-date-asc"));
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
    List<GiftCertificate> actual =
        giftCertificateRepository.getEntityListBySpecification(
            new GetAllGiftCertificatesSpecification("create-date-desc"));
    // then
    assertEquals(actual, expected);
  }

  @Test
  void testGetEntityListBySpecification_shouldReturnEntityListByNamePart_whenEntitiesExist() {
    // given
    List<GiftCertificate> expected = Collections.singletonList(secondGiftCertificate);
    List<GiftCertificate> actual =
        giftCertificateRepository.getEntityListBySpecification(
            new GetGiftCertificatesByNamePartSpecification("sec", null));
    // then
    assertEquals(actual, expected);
  }

  @Test
  void
      testGetEntityListBySpecification_shouldReturnEntityListByDescriptionPart_whenEntitiesExist() {
    // given
    List<GiftCertificate> expected = Collections.singletonList(secondGiftCertificate);
    List<GiftCertificate> actual =
        giftCertificateRepository.getEntityListBySpecification(
            new GetGiftCertificatesByDescriptionPartSpecification("sec", null));
    // then
    assertEquals(actual, expected);
  }

  @Test
  void testGetEntityBySpecification_shouldReturnGift_whenItExists() {
    // given
    // when
    Optional<GiftCertificate> optionalGiftCertificate =
        giftCertificateRepository.getEntityBySpecification(
            new GetGiftCertificateByIdSpecification(2L));
    GiftCertificate actualGiftCertificate = optionalGiftCertificate.get();
    // then
    assertEquals(secondGiftCertificate, actualGiftCertificate);
  }

  @Test
  void testGetEntityBySpecification_shouldReturnEmptyOptional_whenItDoesNotExist() {
    // given
    // when
    Optional<GiftCertificate> optionalGiftCertificate =
        giftCertificateRepository.getEntityBySpecification(
            new GetGiftCertificateByIdSpecification(10L));
    // then
    assertEquals(Optional.empty(), optionalGiftCertificate);
  }

  @Test
  @Transactional
  @Rollback
  void testSave_shouldReturnOptionalGift_whenSavingFinishSuccessfully() {
    // given
    GiftCertificate expectedGiftCertificate = prepareGiftForSave();
    // when
    Optional<GiftCertificate> optionalGiftCertificate =
        giftCertificateRepository.save(expectedGiftCertificate);
    GiftCertificate actualGiftCertificate = optionalGiftCertificate.get();
    expectedGiftCertificate.setCreateDate(actualGiftCertificate.getCreateDate());
    expectedGiftCertificate.setLastUpdateDate(actualGiftCertificate.getLastUpdateDate());
    // then
    assertEquals(optionalGiftCertificate, optionalGiftCertificate);
  }

  private GiftCertificate prepareGiftForSave() {
    GiftCertificate giftCertificate = new GiftCertificate();
    giftCertificate.setName("The fifth");
    giftCertificate.setDescription("The fifth certificate");
    giftCertificate.setPrice(BigDecimal.valueOf(50.0));
    giftCertificate.setDuration(56);
    return giftCertificate;
  }

  @Test
  @Transactional
  @Rollback
  void testUpdate_shouldReturnUpdatedGift_whenUpdatingFinishSuccessfully() {
    // given
    GiftCertificate expectedGiftCertificate = prepareGiftForSave();
    Optional<GiftCertificate> optionalGiftCertificate =
        giftCertificateRepository.save(expectedGiftCertificate);
    GiftCertificate actualGiftCertificate = optionalGiftCertificate.get();
    expectedGiftCertificate.setCreateDate(actualGiftCertificate.getCreateDate());
    expectedGiftCertificate.setLastUpdateDate(actualGiftCertificate.getLastUpdateDate());
    actualGiftCertificate.setName("AnotherName");
    // when
    Optional<GiftCertificate> updatedOptionalGiftCertificate =
        giftCertificateRepository.update(actualGiftCertificate);
    GiftCertificate updatedGiftCertificate = updatedOptionalGiftCertificate.get();
    actualGiftCertificate.setLastUpdateDate(updatedGiftCertificate.getLastUpdateDate());
    // then
    assertEquals(actualGiftCertificate, updatedGiftCertificate);
  }

  @Test
  @Transactional
  @Rollback
  void testDelete_shouldDelete_whenEntityExists() {
    // given
    GiftCertificate giftCertificateForDeleting = preparingForDeleting();
    Optional<GiftCertificate> optionalGiftCertificate =
        giftCertificateRepository.save(giftCertificateForDeleting);
    Long id = optionalGiftCertificate.get().getId();
    // when
    int actualResult = giftCertificateRepository.delete(id);
    // then
    assertEquals(1, actualResult);
  }

  private GiftCertificate preparingForDeleting() {
    GiftCertificate giftCertificateForDeleting = new GiftCertificate();
    giftCertificateForDeleting.setName("DeleteName");
    giftCertificateForDeleting.setDescription("DeleteDescription");
    giftCertificateForDeleting.setPrice(BigDecimal.valueOf(45));
    giftCertificateForDeleting.setDuration(3);
    return giftCertificateForDeleting;
  }
}
