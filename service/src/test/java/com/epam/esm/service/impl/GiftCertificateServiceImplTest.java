package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.repository.GiftCertificateRepository;
import com.epam.esm.dao.repository.TagRepository;
import com.epam.esm.dao.specification.gift.GetGiftCertificatesBySeveralSearchParametersSpecification;
import com.epam.esm.service.builder.certificate.GiftCertificateBuilder;
import com.epam.esm.service.builder.certificate.GiftCertificateDtoBuilder;
import com.epam.esm.service.builder.tag.TagBuilder;
import com.epam.esm.service.builder.tag.TagDtoBuilder;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.EntityNotValidException;
import com.epam.esm.service.exception.EntityNotValidMultipleException;
import com.epam.esm.service.exception.PageNotValidException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.GiftCertificateValidator;
import com.epam.esm.service.validator.PageValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
  @Mock private GiftCertificateRepository giftCertificateRepository;
  @Mock private TagRepository tagRepository;
  @Mock private GiftCertificateBuilder giftCertificateBuilder;
  @Mock private GiftCertificateDtoBuilder giftCertificateDtoBuilder;
  @Mock private GiftCertificateValidator giftCertificateValidator;
  @Mock private PageValidator pageValidator;
  @Mock private TagDtoBuilder tagDtoBuilder;
  @Mock private TagBuilder tagBuilder;

  @InjectMocks private GiftCertificateServiceImpl giftCertificateService;

  private static final int DEFAULT_PAGE_PARAMETER = 1;
  private static final int DEFAULT_SIZE_PARAMETER = 1;
  private static final String FIRST_TAG_NAME = "tag1";
  private static final long FIRST_TAG_ID = 1L;
  private static final long ID_FOR_MANIPULATIONS = 1L;
  private static final String PART_NAME = "va";
  private static final String PART_DESCRIPTION = "es";
  private GiftCertificate expectedGiftCertificate;
  private GiftCertificateDto expectedGiftCertificateDto;
  private GiftCertificate newPriceGiftCertificate;
  private GiftCertificateDto newPriceGiftCertificateDto;
  private List<GiftCertificateDto> expectedGiftCertificateDtos = new ArrayList<>();
  private List<GiftCertificate> expectedGiftCertificates = new ArrayList<>();
  private Set<TagDto> emptyTagSet = new HashSet<>();
  private TagDto firstTagDto = new TagDto(FIRST_TAG_ID, FIRST_TAG_NAME);
  private Tag firstTag = new Tag(FIRST_TAG_ID, FIRST_TAG_NAME);
  private static final long ID = 1L;
  private static final String NAME = "Valid Name";
  private static final String DESCRIPTION = "Valid Description";
  private static final BigDecimal PRICE = BigDecimal.valueOf(12);
  private static final int DURATION = 14;
  private static final LocalDateTime NOW = LocalDateTime.now();

  private GiftCertificateDto changedGiftCertificateDto =
      new GiftCertificateDto(
          ID, "anotherValidName1", DESCRIPTION, PRICE, DURATION, NOW, NOW, emptyTagSet);

  @BeforeEach
  void setUp() {
    expectedGiftCertificate = new GiftCertificate(ID, NAME, DESCRIPTION, PRICE, DURATION, NOW, NOW);

    expectedGiftCertificateDto =
        new GiftCertificateDto(ID, NAME, DESCRIPTION, PRICE, DURATION, NOW, NOW, emptyTagSet);

    newPriceGiftCertificate = expectedGiftCertificate;
    newPriceGiftCertificate.setPrice(BigDecimal.valueOf(24));
    newPriceGiftCertificateDto = expectedGiftCertificateDto;
    newPriceGiftCertificateDto.setPrice(BigDecimal.valueOf(24));
  }

  @Test
  void testSave_shouldReturnGiftCertificateDto_whenSaveFinishSuccessful() {
    // given
    when(giftCertificateRepository.save(expectedGiftCertificate))
        .thenReturn(Optional.of(expectedGiftCertificate));
    when(giftCertificateValidator.isValid(any())).thenReturn(true);
    when(giftCertificateBuilder.build(expectedGiftCertificateDto))
        .thenReturn(expectedGiftCertificate);
    when(giftCertificateDtoBuilder.buildWithTagDtos(expectedGiftCertificate, emptyTagSet))
        .thenReturn(expectedGiftCertificateDto);
    // when
    Optional<GiftCertificateDto> actualGiftCertificateDto =
        giftCertificateService.save(expectedGiftCertificateDto);
    // then
    assertEquals(expectedGiftCertificateDto, actualGiftCertificateDto.get());
  }

  @Test
  void testSave_shouldReturnGiftCertificateDto_whenSaveFinishSuccessfulAndTagNotAttached() {
    // given
    when(giftCertificateRepository.save(expectedGiftCertificate))
        .thenReturn(Optional.of(expectedGiftCertificate));
    when(giftCertificateValidator.isValid(expectedGiftCertificateDto)).thenReturn(true);
    when(giftCertificateBuilder.build(expectedGiftCertificateDto))
        .thenReturn(expectedGiftCertificate);
    when(giftCertificateDtoBuilder.buildWithTagDtos(expectedGiftCertificate, emptyTagSet))
        .thenReturn(expectedGiftCertificateDto);
    // when
    Optional<GiftCertificateDto> actualGiftCertificateDto =
        giftCertificateService.save(expectedGiftCertificateDto);
    // then
    assertEquals(expectedGiftCertificateDto, actualGiftCertificateDto.get());
  }

  @Test
  void testSave_shouldThrowServiceException_whenGiftCertificateIsInvalid() {
    // given
    when(giftCertificateValidator.isValid(any())).thenReturn(false);
    // when
    // then
    assertThrows(
        ServiceException.class, () -> giftCertificateService.save(changedGiftCertificateDto));
  }

  @Test
  void testGetAll_shouldReturnGiftCertificateList_whenGiftCertificatesExist() {
    // given
    expectedGiftCertificates.add(expectedGiftCertificate);
    when(giftCertificateRepository.getEntityListWithPagination(
            any(), anyInt(), anyInt()))
        .thenReturn(expectedGiftCertificates);
    when(giftCertificateBuilder.build(any())).thenReturn(expectedGiftCertificate);
    when(pageValidator.isValid(any())).thenReturn(true);
    // when
    List<GiftCertificateDto> actualGiftCertificateDtos =
        giftCertificateService.getAllByParams(
            DEFAULT_PAGE_PARAMETER, DEFAULT_SIZE_PARAMETER, null, null, null, null);
    List<GiftCertificate> actualGiftCertificates = getGiftCertificates(actualGiftCertificateDtos);
    // then
    assertEquals(expectedGiftCertificates, actualGiftCertificates);
  }

  private List<GiftCertificate> getGiftCertificates(List<GiftCertificateDto> giftCertificateDtos) {
    return giftCertificateDtos.stream()
        .map(actualGiftCertificateDto -> giftCertificateBuilder.build(actualGiftCertificateDto))
        .collect(Collectors.toList());
  }

  @Test
  void testGetAll_shouldReturnThrowPageNotValidException_whenPageParametersNotTransmitted() {
    // given
    when(pageValidator.isValid(any())).thenReturn(false);
    // when
    // then
    assertThrows(
        PageNotValidException.class,
        () -> giftCertificateService.getAllByParams(null, null, null, null, null, null));
  }
  //
  @Test
  void testGetGiftCertificatesByNameOrDescriptionPart_shouldReturnGift_whenFieldsConsistNamePart() {
    // given
    when(pageValidator.isValid(any())).thenReturn(true);
    // when
    List<GiftCertificateDto> actualGiftCertificateDtos =
        giftCertificateService.getAllByParams(
            DEFAULT_PAGE_PARAMETER, DEFAULT_SIZE_PARAMETER, PART_NAME, null, null, null);
    // then
    assertEquals(expectedGiftCertificateDtos, actualGiftCertificateDtos);
  }

  @Test
  void
      testGetGiftCertificatesByNameOrDescriptionPart_shouldReturnGift_whenFieldsConsistDescriptionPart() {
    // given
    when(pageValidator.isValid(any())).thenReturn(true);
    // when
    List<GiftCertificateDto> actualGiftCertificateDtos =
        giftCertificateService.getAllByParams(
            DEFAULT_PAGE_PARAMETER, DEFAULT_SIZE_PARAMETER, null, PART_DESCRIPTION, null, null);
    // then
    assertEquals(expectedGiftCertificateDtos, actualGiftCertificateDtos);
  }

  @Test
  void
      testGetGiftCertificatesByTagName_shouldReturnGiftCertificateList_whenTagExistsAndCollectionNotEmpty() {
    // given
    expectedGiftCertificateDto.setTags(Set.of(firstTagDto));
    expectedGiftCertificate.setTags(Set.of(firstTag));
    List<GiftCertificateDto> expectedGiftCertificateDtos =
        Collections.singletonList((expectedGiftCertificateDto));
    when(pageValidator.isValid(any())).thenReturn(true);
    when(giftCertificateRepository.getEntityListWithPagination(
            new GetGiftCertificatesBySeveralSearchParametersSpecification(
                any(), any(), Collections.singletonList(FIRST_TAG_NAME), any()),
            DEFAULT_PAGE_PARAMETER,
            DEFAULT_SIZE_PARAMETER))
        .thenReturn(Collections.singletonList(expectedGiftCertificate));
    when(giftCertificateDtoBuilder.build(expectedGiftCertificate))
        .thenReturn(expectedGiftCertificateDto);
    // when
    List<GiftCertificateDto> actualGiftCertificateDtos =
        giftCertificateService.getAllByParams(
            DEFAULT_PAGE_PARAMETER,
            DEFAULT_SIZE_PARAMETER,
            null,
            null,
            Collections.singletonList(FIRST_TAG_NAME),
            null);
    // then
    assertEquals(expectedGiftCertificateDtos, actualGiftCertificateDtos);
  }

  @Test
  void
      testGetGiftCertificatesByTagName_shouldReturnGiftCertificateList_whenTagExistsAndCollectionIsEmpty() {
    // given
    when(pageValidator.isValid(any())).thenReturn(true);
    // when
    List<GiftCertificateDto> actualGiftCertificateDtos =
        giftCertificateService.getAllByParams(
            DEFAULT_PAGE_PARAMETER,
            DEFAULT_SIZE_PARAMETER,
            null,
            null,
            Collections.singletonList(FIRST_TAG_NAME),
            null);
    // then
    assertEquals(expectedGiftCertificateDtos, actualGiftCertificateDtos);
  }

  @Test
  void testGetById_shouldReturnGiftCertificate_whenItExists() {
    // given
    when(giftCertificateRepository.getEntity(any()))
        .thenReturn(Optional.of(expectedGiftCertificate));
    when(giftCertificateDtoBuilder.build(any())).thenReturn(expectedGiftCertificateDto);
    // when
    Optional<GiftCertificateDto> optionalActualGiftCertificateDto =
        giftCertificateService.getById(ID_FOR_MANIPULATIONS);
    // then
    assertEquals(expectedGiftCertificateDto, optionalActualGiftCertificateDto.get());
  }

  @Test
  void testGetById_shouldThrowNoSuchElementException_whenItNotExists() {
    // given
    when(giftCertificateRepository.getEntity(any())).thenReturn(Optional.empty());
    // when
    Optional<GiftCertificateDto> optionalActualGiftCertificateDto =
        giftCertificateService.getById(ID_FOR_MANIPULATIONS);
    // then
    assertThrows(
        NoSuchElementException.class,
        () -> giftCertificateBuilder.build(optionalActualGiftCertificateDto.get()));
  }

  @Test
  void testGetById_shouldReturnEmptyOptional_whenGiftDoesNotExist() {
    // given
    when(giftCertificateRepository.getEntity(any())).thenReturn(Optional.empty());
    // when
    Optional<GiftCertificateDto> actualOptionalGiftCertificateDto =
        giftCertificateService.getById(ID_FOR_MANIPULATIONS);
    // then
    assertEquals(Optional.empty(), actualOptionalGiftCertificateDto);
  }

  @Test
  void testUpdate_shouldThrowServiceException_whenGiftCertificateIsInvalid() {
    // given
    when(giftCertificateValidator.isValid(any())).thenReturn(false);
    // when
    // then
    assertThrows(
        ServiceException.class, () -> giftCertificateService.save(expectedGiftCertificateDto));
  }

  @Test
  void testUpdate_shouldThrowServiceException_whenGiftCertificateDoesNotExistInDatabase() {
    // given
    GiftCertificateDto incomingGiftCertificateDto = expectedGiftCertificateDto;
    incomingGiftCertificateDto.setTags(Set.of(firstTagDto));
    when(giftCertificateValidator.isValid(any())).thenReturn(true);
    // when
    // then
    assertThrows(
        ServiceException.class,
        () -> giftCertificateService.update(ID_FOR_MANIPULATIONS, incomingGiftCertificateDto));
  }

  @Test
  void testUpdate_shouldThrowServiceException_whenTagWasNotSaved() {
    // given
    when(giftCertificateRepository.getEntity(any()))
        .thenReturn(Optional.of(expectedGiftCertificate));
    GiftCertificateDto incomingGiftCertificateDto = expectedGiftCertificateDto;
    incomingGiftCertificateDto.setTags(Set.of(firstTagDto));
    when(giftCertificateValidator.isValid(any())).thenReturn(true);
    // when
    // then
    assertThrows(
        ServiceException.class,
        () -> giftCertificateService.update(ID_FOR_MANIPULATIONS, incomingGiftCertificateDto));
  }

  @Test
  void testUpdate_shouldThrowEntityNotValidMultipleException_whenGiftNotValid() {
    // given
    when(giftCertificateValidator.isValid(any())).thenReturn(false);
    // when
    // then
    assertThrows(
        EntityNotValidException.class,
        () -> giftCertificateService.update(ID_FOR_MANIPULATIONS, expectedGiftCertificateDto));
  }

  @Test
  void testUpdate_shouldDeleteTags_whenItWasNotTransmitted() {
    // given
    when(giftCertificateValidator.isValid(any())).thenReturn(true);
    when(giftCertificateRepository.getEntity(any()))
        .thenReturn(Optional.ofNullable(expectedGiftCertificate));
    when(tagRepository.getEntity(any())).thenReturn(Optional.of(firstTag));
    when(tagDtoBuilder.build(firstTag)).thenReturn(firstTagDto);
    when(tagBuilder.build(firstTagDto)).thenReturn(firstTag);
    when(giftCertificateRepository.update(any())).thenReturn(Optional.of(expectedGiftCertificate));
    GiftCertificateDto incomingGiftCertificateDto = expectedGiftCertificateDto;
    incomingGiftCertificateDto.setTags(Set.of(firstTagDto));
    when(giftCertificateDtoBuilder.build(any())).thenReturn(changedGiftCertificateDto);
    when(tagRepository.getEntityList(any()))
        .thenReturn(Collections.singletonList(firstTag));
    // when
    Optional<GiftCertificateDto> actualGiftCertificateDto =
        giftCertificateService.update(ID_FOR_MANIPULATIONS, incomingGiftCertificateDto);
    // then
    assertEquals(changedGiftCertificateDto, actualGiftCertificateDto.get());
  }

  @Test
  void testUpdate_shouldThrowEntityNotFoundException_whenGiftNotExistsInDatabase() {
    // given
    when(giftCertificateValidator.isValid(any())).thenReturn(true);
    when(giftCertificateRepository.getEntity(any()))
        .thenReturn(Optional.ofNullable(expectedGiftCertificate));
    when(tagRepository.getEntity(any())).thenReturn(Optional.of(firstTag));
    when(tagDtoBuilder.build(firstTag)).thenReturn(firstTagDto);
    when(tagBuilder.build(firstTagDto)).thenReturn(firstTag);
    when(giftCertificateRepository.update(any())).thenReturn(Optional.of(expectedGiftCertificate));
    GiftCertificateDto incomingGiftCertificateDto = expectedGiftCertificateDto;
    incomingGiftCertificateDto.setTags(Set.of(firstTagDto));
    when(giftCertificateRepository.update(any())).thenReturn(Optional.empty());
    when(tagRepository.getEntityList(any()))
        .thenReturn(Collections.singletonList(firstTag));
    // when
    // then
    assertThrows(
        EntityNotFoundException.class,
        () -> giftCertificateService.update(ID_FOR_MANIPULATIONS, incomingGiftCertificateDto));
  }

  @Test
  void testUpdate_shouldThrowEntityNotFoundException_whenTagCannotCreatedInDB() {
    // given
    when(giftCertificateRepository.getEntity(any()))
        .thenReturn(Optional.of(expectedGiftCertificate));
    GiftCertificateDto incomingGiftCertificateDto = expectedGiftCertificateDto;
    incomingGiftCertificateDto.setTags(Set.of(firstTagDto));
    when(giftCertificateValidator.isValid(any())).thenReturn(true);
    // when
    // then
    assertThrows(
        EntityNotFoundException.class,
        () -> giftCertificateService.update(ID_FOR_MANIPULATIONS, incomingGiftCertificateDto));
  }

  @Test
  void testUpdateOneField_shouldUpdatePrice_whenPriceIsValid() {
    // given
    when(giftCertificateValidator.isValid(any())).thenReturn(true);
    when(giftCertificateRepository.getEntity(any()))
        .thenReturn(Optional.of(expectedGiftCertificate));
    when(giftCertificateDtoBuilder.build(expectedGiftCertificate))
        .thenReturn(expectedGiftCertificateDto);
    when(giftCertificateRepository.update(expectedGiftCertificate))
        .thenReturn(Optional.of(newPriceGiftCertificate));
    // when
    Optional<GiftCertificateDto> actualOptionalGiftCertificate =
        giftCertificateService.updateOneField(ID_FOR_MANIPULATIONS, expectedGiftCertificateDto);
    // then
    assertEquals(Optional.of(newPriceGiftCertificateDto), actualOptionalGiftCertificate);
  }

  @Test
  void testUpdateOneField_shouldThrowEntityNotValidMultipleException_whenPriceIsInvalid() {
    // given
    when(giftCertificateValidator.isValid(any())).thenReturn(false);
    when(giftCertificateDtoBuilder.build(any())).thenReturn(expectedGiftCertificateDto);
    when(giftCertificateRepository.getEntity(any()))
        .thenReturn(Optional.ofNullable(expectedGiftCertificate));
    // when
    // then
    assertThrows(
        EntityNotValidMultipleException.class,
        () ->
            giftCertificateService.updateOneField(
                ID_FOR_MANIPULATIONS, expectedGiftCertificateDto));
  }

  @Test
  void testDelete_shouldDeleteGift_whenGiftExistsInDatabase() {
    // given
    int expectedResult = 1;
    when(giftCertificateRepository.delete(ID_FOR_MANIPULATIONS)).thenReturn(expectedResult);
    when(giftCertificateRepository.getEntity(any()))
        .thenReturn(Optional.of(expectedGiftCertificate));
    // when
    int actualResult = giftCertificateService.delete(ID_FOR_MANIPULATIONS);
    // then
    assertEquals(expectedResult, actualResult);
  }

  @Test
  void testDelete_shouldThrowEntityNotFoundException_whenItNotExistsInDatabase() {
    // given
    when(giftCertificateRepository.getEntity(any())).thenReturn(Optional.empty());
    // when
    // then
    assertThrows(
        EntityNotFoundException.class, () -> giftCertificateService.delete(ID_FOR_MANIPULATIONS));
  }
}
