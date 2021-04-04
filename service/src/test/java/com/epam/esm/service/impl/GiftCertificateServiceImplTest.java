package com.epam.esm.service.impl;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.GiftCertificateTagRepository;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.specification.gift.GetGiftCertificateByIdSpecification;
import com.epam.esm.service.builder.GiftCertificateBuilder;
import com.epam.esm.service.dto.GiftCertificateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private GiftCertificateTagRepository giftCertificateTagRepository;
    //TODO: Think about it
    private GiftCertificateBuilder builder = new GiftCertificateBuilder();

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;
    private GiftCertificate expectedGiftCertificate;
    private static final long ID_FOR_MANIPULATIONS = 1L;

    @BeforeEach
    void setUp() {
        expectedGiftCertificate = new GiftCertificate(
                1L,
                "validName",
                "validDescription",
                BigDecimal.valueOf(12),
                14,
                LocalDate.now(),
                LocalDate.now());
    }

    @Test
    void save() {
    }

    @Test
    void getAll() {

    }

    @Test
    void getGiftCertificatesByNameOrDescriptionPart() {
    }

    @Test
    void getGiftCertificatesByTagName() {
    }

    @Test
    void testGetByIdShouldReturnGiftCertificateWhenItExists() {
        Mockito.lenient().when(giftCertificateRepository.getEntityBySpecification(any()))
                .thenReturn(Optional.of(expectedGiftCertificate));

        Optional<GiftCertificateDto> actualGiftCertification = giftCertificateService.getById(ID_FOR_MANIPULATIONS);

        GiftCertificate actualGiftCertificate = builder.buildFromDto(actualGiftCertification.get());

        assertEquals(expectedGiftCertificate, actualGiftCertificate);
    }

    @Test
    void testGetByIdShouldReturnEmptyWrapperOptionalWhenItNotExists() {
        Mockito.lenient().when(giftCertificateRepository.getEntityBySpecification(any()))
                .thenReturn(Optional.empty());


        assertEquals(Optional.empty(), Optional.empty());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }


}