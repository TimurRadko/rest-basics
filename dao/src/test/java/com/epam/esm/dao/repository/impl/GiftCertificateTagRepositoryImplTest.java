package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.config.TestDaoConfig;
import com.epam.esm.dao.entity.GiftCertificateTag;
import com.epam.esm.dao.specification.gifttag.GetGiftCertificateTagByGiftCertificateIdSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestDaoConfig.class})
@ActiveProfiles("test")
class GiftCertificateTagRepositoryImplTest {
  private GiftCertificateTag firstGiftCertificateTag;

  @Autowired private GiftCertificateTagRepositoryImpl giftCertificateTagRepository;

  @BeforeEach
  void setUp() {
    firstGiftCertificateTag = new GiftCertificateTag(1L, 1L, 1L);
  }

//  @Test
//  void testGetEntityBySpecification_shouldReturnEntity_whenEntityIsExists() {
//    // given
//    // when
//    Optional<GiftCertificateTag> optionalActualGiftCertificateTag =
//        giftCertificateTagRepository.getEntityBySpecification(
//            new GetGiftCertificateTagByGiftCertificateIdSpecification(1L, 1L));
//    GiftCertificateTag actualGiftCertificateTag = optionalActualGiftCertificateTag.get();
//    // then
//    assertEquals(firstGiftCertificateTag, actualGiftCertificateTag);
//  }
//
//  @Test
//  void testGetEntityBySpecification_shouldReturnEmptyOptional_whenEntityDoesNotExist() {
//    // given
//    // when
//    Optional<GiftCertificateTag> optionalActualGiftCertificateTag =
//        giftCertificateTagRepository.getEntityBySpecification(
//            new GetGiftCertificateTagByGiftCertificateIdSpecification(10L, 10L));
//    // then
//    assertEquals(Optional.empty(), optionalActualGiftCertificateTag);
//  }
//
//  @Test
//  @Transactional
//  @Rollback
//  void testSave_shouldReturnGiftCertificateTag_whenSavingFinishSuccessfully() {
//    // given
//    GiftCertificateTag expectedGiftCertificateTag = new GiftCertificateTag();
//    expectedGiftCertificateTag.setGiftCertificateId(2L);
//    expectedGiftCertificateTag.setTagId(2L);
//    // when
//    Optional<GiftCertificateTag> optionalActualGiftCertificateTag =
//        giftCertificateTagRepository.save(expectedGiftCertificateTag);
//    GiftCertificateTag actualGiftCertificateTag = optionalActualGiftCertificateTag.get();
//    expectedGiftCertificateTag.setId(actualGiftCertificateTag.getTagId());
//    // then
//    assertEquals(actualGiftCertificateTag, expectedGiftCertificateTag);
//  }
//
//  @Test
//  void testGetEntityListBySpecification_shouldReturnGiftTagList_whenGiftTagsAreExist() {
//    // given
//    // when
//    List<GiftCertificateTag> giftCertificateTags =
//        giftCertificateTagRepository.getEntityListBySpecification(
//            new GetGiftCertificateTagByGiftCertificateIdSpecification(1L, 1L));
//    // then
//    assertEquals(Collections.singletonList(firstGiftCertificateTag), giftCertificateTags);
//  }
}
