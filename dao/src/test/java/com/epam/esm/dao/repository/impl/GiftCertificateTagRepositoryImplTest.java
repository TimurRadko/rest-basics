package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.config.TestDaoConfig;
import com.epam.esm.dao.entity.GiftCertificateTag;
import com.epam.esm.dao.specification.gifttag.GetGiftCertificateTagByGiftCertificateIdSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

  @Test
  void getEntityBySpecification() {
    // given
    // when
    Optional<GiftCertificateTag> optionalActualGiftCertificateTag =
        giftCertificateTagRepository.getEntityBySpecification(
            new GetGiftCertificateTagByGiftCertificateIdSpecification(1L, 1L));
    GiftCertificateTag actualGiftCertificateTag = optionalActualGiftCertificateTag.get();
    // then
    assertEquals(firstGiftCertificateTag, actualGiftCertificateTag);
  }
}
