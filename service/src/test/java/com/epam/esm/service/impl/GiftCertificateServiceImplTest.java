package com.epam.esm.service.impl;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.GiftCertificateTagRepository;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.specification.gift.GetGiftCertificatesByPartNameOrDescriptionSpecification;
import com.epam.esm.persistence.specification.gift.GetGiftCertificatesByTagNameSpecification;
import com.epam.esm.service.builder.GiftCertificateBuilder;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.GiftCertificateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private GiftCertificateBuilder builder;
    @Mock
    private GiftCertificateValidator giftCertificateValidator;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;
    private GiftCertificate firstExpectedGiftCertificate;
    private GiftCertificate secondExpectedGiftCertificate;

    private static final long ID_FOR_MANIPULATIONS = 1L;
    private static final String NULL_SORTING = null;
    private List<GiftCertificateDto> expectedGiftCertificateDtos = new ArrayList<>();
    private List<GiftCertificate> expectedGiftCertificates = new ArrayList<>();
    private Set<Tag> tags = new HashSet<>();
    private Tag firstTag = new Tag(1L, "tag1");
    private Tag secondTag = new Tag(2L, "tag2");
    private static final String PART_NAME = "va";
    private static final String PART_DESCRIPTION = "es";
    private static final String FIRST_TAG_NAME = "tag1";

    @BeforeEach
    void setUp() {
        firstExpectedGiftCertificate = new GiftCertificate(
                1L,
                "validName1",
                "validDescription1",
                BigDecimal.valueOf(12),
                14,
                LocalDate.now(),
                LocalDate.now());
        secondExpectedGiftCertificate = new GiftCertificate(
                2L,
                "lidName2",
                "validDcription2",
                BigDecimal.valueOf(24),
                28,
                LocalDate.now(),
                LocalDate.now());
    }

    @Test
    void testSaveShouldReturnCorrectGiftCertificateDtoWhenSaveWasSuccessful() {
        GiftCertificateDto expectedGiftCertificateDto = new GiftCertificateDto(firstExpectedGiftCertificate, tags);
        Mockito.lenient().when(giftCertificateRepository.save(firstExpectedGiftCertificate))
                .thenReturn(Optional.of(firstExpectedGiftCertificate));
        Mockito.lenient().when(giftCertificateValidator.validate(expectedGiftCertificateDto)).thenReturn(true);
        Mockito.lenient().when(builder.buildFromDto(expectedGiftCertificateDto)).thenReturn(firstExpectedGiftCertificate);

        Optional<GiftCertificateDto> actualGiftCertificateDto =
                giftCertificateService.save(expectedGiftCertificateDto);

        assertEquals(expectedGiftCertificateDto, actualGiftCertificateDto.get());
    }

    @Test
    void testSaveShouldThrowServiceExceptionWhenGiftCertificateIsInvalid() {
        Mockito.lenient()
                .when(giftCertificateRepository.save(firstExpectedGiftCertificate))
                .thenReturn(Optional.of(firstExpectedGiftCertificate));
        Mockito.lenient().when(giftCertificateValidator.validate(any())).thenReturn(false);
        GiftCertificateDto expectedGiftCertificateDto = new GiftCertificateDto(firstExpectedGiftCertificate, tags);
        assertThrows(ServiceException.class, () -> giftCertificateService.save(expectedGiftCertificateDto));
    }

    @Test
    void testGetAllShouldReturnGiftCertificatesListWhenGiftCertificatesExist() {
        expectedGiftCertificates.add(firstExpectedGiftCertificate);

        Mockito.lenient().when(giftCertificateRepository.getEntitiesListBySpecification(any()))
                .thenReturn(expectedGiftCertificates);
        Mockito.lenient().when(builder.buildFromDto(any())).thenReturn(firstExpectedGiftCertificate);
        List<GiftCertificateDto> actualGiftCertificateDtos = giftCertificateService.getAll(NULL_SORTING);

        List<GiftCertificate> actualGiftCertificates = getGiftCertificates(actualGiftCertificateDtos);

        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    private List<GiftCertificate> getGiftCertificates(List<GiftCertificateDto> giftCertificateDtos) {
        return giftCertificateDtos
                .stream()
                .map(actualGiftCertificateDto -> builder.buildFromDto(actualGiftCertificateDto))
                .collect(Collectors.toList());
    }

    @Test
    void testGetGiftCertificatesByNameOrDescriptionPartShouldReturnGiftCertificateWhenPartOfNameMatchesWithPartNameExistingGiftCertificate() {
        Mockito.lenient()
                .when(giftCertificateRepository
                        .getEntitiesListBySpecification(
                                new GetGiftCertificatesByPartNameOrDescriptionSpecification(
                                        PART_NAME,
                                        null,
                                        null)))
                .thenReturn(expectedGiftCertificates);

        List<GiftCertificateDto> actualGiftCertificateDtos
                = giftCertificateService.getGiftCertificatesByNameOrDescriptionPart(
                PART_NAME,
                null,
                null);

        assertEquals(expectedGiftCertificateDtos, actualGiftCertificateDtos);
    }

    @Test
    void testGetGiftCertificatesByNameOrDescriptionPartShouldReturnGiftCertificateWhenPartOfDescriptionMatchesWithPartDescriptionExistingGiftCertificate() {
        Mockito.lenient()
                .when(giftCertificateRepository
                        .getEntitiesListBySpecification(
                                new GetGiftCertificatesByPartNameOrDescriptionSpecification(
                                        null,
                                        PART_DESCRIPTION,
                                        null)))
                .thenReturn(expectedGiftCertificates);

        List<GiftCertificateDto> actualGiftCertificateDtos
                = giftCertificateService.getGiftCertificatesByNameOrDescriptionPart(
                null,
                PART_DESCRIPTION,
                null);

        assertEquals(expectedGiftCertificateDtos, actualGiftCertificateDtos);
    }

    @Test
    void testGetGiftCertificatesByTagNameShouldReturnGiftCertificateListWhenTagNameExistsAndCollectionsNotEmpty() {
        List<GiftCertificateDto> incomingGiftCertificateDtos = Arrays.asList(
                new GiftCertificateDto(firstExpectedGiftCertificate, Set.of(firstTag)),
                new GiftCertificateDto(secondExpectedGiftCertificate, Set.of(secondTag))
        );
        List<GiftCertificateDto> expectedGiftCertificateDtos =
                Collections.singletonList((new GiftCertificateDto(firstExpectedGiftCertificate, Set.of(firstTag))));

        Mockito.lenient()
                .when(giftCertificateRepository
                        .getEntitiesListBySpecification(
                                new GetGiftCertificatesByTagNameSpecification(
                                        FIRST_TAG_NAME,
                                        null)))
                .thenReturn(Collections.singletonList(firstExpectedGiftCertificate));

        List<GiftCertificateDto> actualGiftCertificateDtos
                = giftCertificateService.getGiftCertificatesByTagName(
                incomingGiftCertificateDtos,
                FIRST_TAG_NAME,
                null);

        assertEquals(expectedGiftCertificateDtos, actualGiftCertificateDtos);
    }

    @Test
    void testGetGiftCertificatesByTagNameShouldReturnGiftCertificateListWhenTagNameExistsAndCollectionsIsEmpty() {
        List<GiftCertificateDto> incomingGiftCertificateDtos = new ArrayList<>();

        List<GiftCertificate> expectedGiftCertificates = getGiftCertificates(incomingGiftCertificateDtos);

        Mockito.lenient()
                .when(giftCertificateRepository
                        .getEntitiesListBySpecification(
                                new GetGiftCertificatesByTagNameSpecification(
                                        FIRST_TAG_NAME,
                                        null)))
                .thenReturn(expectedGiftCertificates);

        List<GiftCertificateDto> actualGiftCertificateDtos
                = giftCertificateService.getGiftCertificatesByTagName(
                incomingGiftCertificateDtos,
                FIRST_TAG_NAME,
                null);

        assertEquals(expectedGiftCertificates, getGiftCertificates(actualGiftCertificateDtos));
    }

    @Test
    void testGetByIdShouldReturnGiftCertificateWhenItExists() {
        Mockito.lenient().when(giftCertificateRepository.getEntityBySpecification(any()))
                .thenReturn(Optional.of(firstExpectedGiftCertificate));
        Optional<GiftCertificateDto> optionalActualGiftCertificateDto = giftCertificateService.getById(ID_FOR_MANIPULATIONS);
        GiftCertificate actualGiftCertificate = buildFromDto(optionalActualGiftCertificateDto.get());
        Mockito.lenient()
                .when(builder.buildFromDto(optionalActualGiftCertificateDto.get()))
                .thenReturn(actualGiftCertificate);

        assertEquals(firstExpectedGiftCertificate, actualGiftCertificate);
    }

    private GiftCertificate buildFromDto(GiftCertificateDto giftCertificateDto) {
        return new GiftCertificate(giftCertificateDto.getId(),
                giftCertificateDto.getName(),
                giftCertificateDto.getDescription(),
                giftCertificateDto.getPrice(),
                giftCertificateDto.getDuration(),
                giftCertificateDto.getCreateDate(),
                giftCertificateDto.getLastUpdateDate());
    }

    @Test
    void testGetByIdShouldReturnEmptyWrapperOptionalWhenItNotExists() {
        Mockito.lenient().when(giftCertificateRepository.getEntityBySpecification(any()))
                .thenReturn(Optional.empty());

        Optional<GiftCertificateDto> actualOptionalGiftCertificateDto
                = giftCertificateService.getById(ID_FOR_MANIPULATIONS);

        assertEquals(Optional.empty(), actualOptionalGiftCertificateDto);
    }

    @Test
    void testUpdateShouldThrowServiceExceptionWhenGiftCertificateInvalid() {
        Mockito.lenient()
                .when(giftCertificateRepository.update(firstExpectedGiftCertificate))
                .thenReturn(Optional.of(firstExpectedGiftCertificate));
        Mockito.lenient().when(giftCertificateValidator.validate(any())).thenReturn(false);
        GiftCertificateDto expectedGiftCertificateDto = new GiftCertificateDto(firstExpectedGiftCertificate, tags);
        assertThrows(ServiceException.class, () -> giftCertificateService.save(expectedGiftCertificateDto));
    }

    @Test
    void testUpdateShouldReturnUpdatedGiftCertificateWhenGiftCertificateIsValid() {
//        GiftCertificate expectedChangedGiftCertificate = firstExpectedGiftCertificate;
//        expectedChangedGiftCertificate.setName("AnotherName");
//
//        Mockito.lenient()
//                .when(giftCertificateRepository.update(firstExpectedGiftCertificate))
//                .thenReturn(Optional.of(expectedChangedGiftCertificate));
//
//        GiftCertificateDto incomingGiftCertificateDto
//                = new GiftCertificateDto(firstExpectedGiftCertificate, Set.of(firstTag));
//        Mockito.lenient().when(giftCertificateValidator.validate(any())).thenReturn(true);
//        Mockito.lenient().when(tagRepository.save(any())).thenReturn(Optional.of(firstTag));
////        Mockito.lenient().when(giftCertificateRepository.getEntityBySpecification(any()))
////                .thenReturn(Optional.of(firstExpectedGiftCertificate));
//        Optional<GiftCertificateDto> optionalActualGiftCertificateDto
//                = giftCertificateService.update(ID_FOR_MANIPULATIONS, incomingGiftCertificateDto);
//
//        GiftCertificate actualGiftCertificate = buildFromDto(optionalActualGiftCertificateDto.get());
//
//        assertEquals(expectedChangedGiftCertificate, actualGiftCertificate);

    }

    @Test
    void testDeleteGiftCertificatesWhenDeleteCertificateExistsInDatabase() {
        int expectedResult = 1;
        Mockito.lenient().when(giftCertificateRepository.delete(ID_FOR_MANIPULATIONS)).thenReturn(expectedResult);
        int actualResult = giftCertificateService.delete(ID_FOR_MANIPULATIONS);
        assertEquals(expectedResult, actualResult);
    }
}