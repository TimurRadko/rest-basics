package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.config.TestPersistenceConfig;
import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.specification.gift.GetAllGiftCertificatesSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestPersistenceConfig.class})
class GiftCertificateRepositoryImplTest {
    private GiftCertificate firstGiftCertificate;
    private GiftCertificate secondGiftCertificate;
    private GiftCertificate thirdGiftCertificate;

    private GiftCertificateRepositoryImpl giftCertificateRepository;

    @Autowired
    public GiftCertificateRepositoryImplTest(GiftCertificateRepositoryImpl giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
    }

    //    @BeforeEach
    void setUp() {
        firstGiftCertificate = new GiftCertificate(
                1L,
                "the first gift certificate",
                "the description of the first gift certificate",
                BigDecimal.valueOf(45),
                43,
                LocalDateTime.now(),
                LocalDateTime.now()
                );

        secondGiftCertificate = new GiftCertificate(
                2L,
                "the second gift certificate",
                "the description of the second gift certificate",
                BigDecimal.valueOf(90),
                86,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        thirdGiftCertificate = new GiftCertificate(
                1L,
                "the third gift certificate",
                "the description 0f the third gift certificate",
                BigDecimal.valueOf(43),
                33,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    void getEntitiesListBySpecification() {
//        List<GiftCertificate> expected = Arrays.asList(
//                firstGiftCertificate,
//                secondGiftCertificate,
//                thirdGiftCertificate);
//        List<GiftCertificate> actual
//                = giftCertificateRepository.getEntitiesListBySpecification
//                (new GetAllGiftCertificatesSpecification(null));
//        assertEquals(actual, expected);
    }

    @Test
    void getEntityBySpecification() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }
}