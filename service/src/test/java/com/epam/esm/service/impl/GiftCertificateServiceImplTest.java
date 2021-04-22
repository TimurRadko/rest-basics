package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.GiftCertificateTag;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.repository.GiftCertificateRepository;
import com.epam.esm.dao.repository.TagRepository;
import com.epam.esm.service.builder.certificate.GiftCertificateBuilder;
import com.epam.esm.service.builder.certificate.GiftCertificateDtoBuilder;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.validator.GiftCertificateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
  @Mock private GiftCertificateRepository giftCertificateRepository;
  @Mock private TagRepository tagRepository;
  @Mock private GiftCertificateBuilder giftCertificateBuilder;
  @Mock private GiftCertificateDtoBuilder giftCertificateDtoBuilder;
  @Mock private GiftCertificateValidator giftCertificateValidator;

  @InjectMocks private GiftCertificateServiceImpl giftCertificateService;
  private GiftCertificate expectedGiftCertificate;
  private GiftCertificateDto expectedGiftCertificateDto;
  private LocalDateTime now = LocalDateTime.now();

  private static final long ID_FOR_MANIPULATIONS = 1L;
  private List<GiftCertificateDto> expectedGiftCertificateDtos = new ArrayList<>();
  private List<GiftCertificate> expectedGiftCertificates = new ArrayList<>();
  private Set<TagDto> emptyTagSet = new HashSet<>();
  private Tag firstTag = new Tag(1L, "tag1");
  private static final String PART_NAME = "va";
  private static final String PART_DESCRIPTION = "es";
  private static final String FIRST_TAG_NAME = "tag1";
  private GiftCertificateTag giftCertificateTag = new GiftCertificateTag(1L, 1L, 1L);
  private GiftCertificateDto changedGiftCertificateDto =
      new GiftCertificateDto(
          1L,
          "anotherValidName1",
          "validDescription1",
          BigDecimal.valueOf(12),
          14,
          LocalDateTime.now(),
          LocalDateTime.now(),
          emptyTagSet);

  @BeforeEach
  void setUp() {
    expectedGiftCertificate =
        new GiftCertificate(
            1L, "validName1", "validDescription1", BigDecimal.valueOf(12), 14, now, now);

    expectedGiftCertificateDto =
        new GiftCertificateDto(
            1L,
            "validName1",
            "validDescription1",
            BigDecimal.valueOf(12),
            14,
            now,
            now,
            emptyTagSet);
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

//    @Test
//    void testSave_shouldReturnGiftCertificateDto_whenSaveFinishSuccessfulAndTagNotAttached() {
//      // given
//      GiftCertificateDto expectedGiftCertificateDto =
//          new GiftCertificateDto(firstExpectedGiftCertificateDto, Set.of(firstTag));
//      when(giftCertificateRepository.save(firstExpectedGiftCertificateDto))
//          .thenReturn(Optional.of(firstExpectedGiftCertificateDto));
//      when(giftCertificateValidator.isValid(expectedGiftCertificateDto)).thenReturn(true);
//      when(tagRepository.getEntityBySpecification(any())).thenReturn(Optional.of(firstTag));
//
//   when(builder.buildFromDto(expectedGiftCertificateDto)).thenReturn(firstExpectedGiftCertificateDto);
//      // when
//      Optional<GiftCertificateDto> actualGiftCertificateDto =
//          giftCertificateService.save(expectedGiftCertificateDto);
//      // then
//      assertEquals(expectedGiftCertificateDto, actualGiftCertificateDto.get());
//    }
  //
  //  @Test
  //  void testSave_shouldThrowServiceException_whenGiftCertificateIsInvalid() {
  //    // given
  //    when(giftCertificateValidator.isValid(any())).thenReturn(false);
  //    GiftCertificateDto expectedGiftCertificateDto =
  //        new GiftCertificateDto(firstExpectedGiftCertificateDto, emptyTagSet);
  //    // when
  //    // then
  //    assertThrows(
  //        ServiceException.class, () -> giftCertificateService.save(expectedGiftCertificateDto));
  //  }
  //
  //  @Test
  //  void testGetAll_shouldReturnGiftCertificateList_whenGiftCertificatesExist() {
  //    // given
  //    expectedGiftCertificates.add(firstExpectedGiftCertificateDto);
  //    when(giftCertificateRepository.getEntityListBySpecification(any()))
  //        .thenReturn(expectedGiftCertificates);
  //    when(builder.buildFromDto(any())).thenReturn(firstExpectedGiftCertificateDto);
  //    // when
  //    List<GiftCertificateDto> actualGiftCertificateDtos =
  //        giftCertificateService.getAllByParams(null, null, null, null);
  //    List<GiftCertificate> actualGiftCertificates =
  // getGiftCertificates(actualGiftCertificateDtos);
  //    // then
  //    assertEquals(expectedGiftCertificates, actualGiftCertificates);
  //  }
  //
  //  private List<GiftCertificate> getGiftCertificates(List<GiftCertificateDto>
  // giftCertificateDtos) {
  //    return giftCertificateDtos.stream()
  //        .map(actualGiftCertificateDto -> builder.buildFromDto(actualGiftCertificateDto))
  //        .collect(Collectors.toList());
  //  }
  //
  //  @Test
  //  void
  // testGetGiftCertificatesByNameOrDescriptionPart_shouldReturnGift_whenFieldsConsistNamePart() {
  //    // given
  //    // when
  //    List<GiftCertificateDto> actualGiftCertificateDtos =
  //        giftCertificateService.getAllByParams(PART_NAME, null, null, null);
  //    // then
  //    assertEquals(expectedGiftCertificateDtos, actualGiftCertificateDtos);
  //  }
  //
  //  @Test
  //  void
  //
  // testGetGiftCertificatesByNameOrDescriptionPart_shouldReturnGift_whenFieldsConsistDescriptionPart() {
  //    // given
  //    // when
  //    List<GiftCertificateDto> actualGiftCertificateDtos =
  //        giftCertificateService.getAllByParams(null, PART_DESCRIPTION, null, null);
  //    // then
  //    assertEquals(expectedGiftCertificateDtos, actualGiftCertificateDtos);
  //  }
  //
  //  @Test
  //  void
  //
  // testGetGiftCertificatesByTagName_shouldReturnGiftCertificateList_whenTagExistsAndCollectionNotEmpty() {
  //    // given
  //    List<GiftCertificateDto> expectedGiftCertificateDtos =
  //        Collections.singletonList(
  //            (new GiftCertificateDto(firstExpectedGiftCertificateDto, Set.of(firstTag))));
  //    when(giftCertificateRepository.getEntityListBySpecification(any()))
  //        .thenReturn(Collections.singletonList(firstExpectedGiftCertificateDto));
  //    when(tagRepository.getEntityListBySpecification(any()))
  //        .thenReturn(Collections.singletonList(firstTag));
  //    // when
  //    List<GiftCertificateDto> actualGiftCertificateDtos =
  //        giftCertificateService.getAllByParams(null, null, FIRST_TAG_NAME, null);
  //    // then
  //    assertEquals(expectedGiftCertificateDtos, actualGiftCertificateDtos);
  //  }
  //
  //  @Test
  //  void
  //
  // testGetGiftCertificatesByTagName_shouldReturnGiftCertificateList_whenTagExistsAndCollectionIsEmpty() {
  //    // given
  //    List<GiftCertificateDto> expectedGiftCertificateDtos =
  //        Collections.singletonList(
  //            (new GiftCertificateDto(firstExpectedGiftCertificateDto, emptyTagSet)));
  //    when(giftCertificateRepository.getEntityListBySpecification(any()))
  //        .thenReturn(Collections.singletonList(firstExpectedGiftCertificateDto));
  //    // when
  //    List<GiftCertificateDto> actualGiftCertificateDtos =
  //        giftCertificateService.getAllByParams(null, null, FIRST_TAG_NAME, null);
  //    // then
  //    assertEquals(expectedGiftCertificateDtos, actualGiftCertificateDtos);
  //  }
  //
  //  @Test
  //  void testGetById_shouldReturnGiftCertificate_whenItExists() {
  //    // given
  //    when(giftCertificateRepository.getEntityBySpecification(any()))
  //        .thenReturn(Optional.of(firstExpectedGiftCertificateDto));
  //    // when
  //    Optional<GiftCertificateDto> optionalActualGiftCertificateDto =
  //        giftCertificateService.getById(ID_FOR_MANIPULATIONS);
  //    GiftCertificate actualGiftCertificate =
  // buildFromDto(optionalActualGiftCertificateDto.get());
  //    // then
  //    assertEquals(firstExpectedGiftCertificateDto, actualGiftCertificate);
  //  }
  //
  //  private GiftCertificate buildFromDto(GiftCertificateDto giftCertificateDto) {
  //    return new GiftCertificate(
  //        giftCertificateDto.getId(),
  //        giftCertificateDto.getName(),
  //        giftCertificateDto.getDescription(),
  //        giftCertificateDto.getPrice(),
  //        giftCertificateDto.getDuration(),
  //        giftCertificateDto.getCreateDate(),
  //        giftCertificateDto.getLastUpdateDate());
  //  }
  //
  //  @Test
  //  void testGetById_shouldReturnEmptyOptional_whenGiftDoesNotExist() {
  //    // given
  //
  // when(giftCertificateRepository.getEntityBySpecification(any())).thenReturn(Optional.empty());
  //    // when
  //    Optional<GiftCertificateDto> actualOptionalGiftCertificateDto =
  //        giftCertificateService.getById(ID_FOR_MANIPULATIONS);
  //    // then
  //    assertEquals(Optional.empty(), actualOptionalGiftCertificateDto);
  //  }
  //
  //  @Test
  //  void testUpdate_shouldThrowServiceException_whenGiftCertificateIsInvalid() {
  //    // given
  //    when(giftCertificateValidator.isValid(any())).thenReturn(false);
  //    GiftCertificateDto expectedGiftCertificateDto =
  //        new GiftCertificateDto(firstExpectedGiftCertificateDto, emptyTagSet);
  //    // when
  //    // then
  //    assertThrows(
  //        ServiceException.class, () -> giftCertificateService.save(expectedGiftCertificateDto));
  //  }
  //
  //  @Test
  //  void testUpdate_shouldThrowServiceException_whenGiftCertificateDoesNotExistInDatabase() {
  //    // given
  //    GiftCertificateDto incomingGiftCertificateDto =
  //        new GiftCertificateDto(firstExpectedGiftCertificateDto, Set.of(firstTag));
  //
  //    when(giftCertificateValidator.isValid(any())).thenReturn(true);
  //    when(builder.buildFromDto(any())).thenReturn(changedGiftCertificateDto);
  //    // when
  //    // then
  //    assertThrows(
  //        ServiceException.class,
  //        () -> giftCertificateService.update(ID_FOR_MANIPULATIONS, incomingGiftCertificateDto));
  //  }
  //
  //  @Test
  //  void testUpdate_shouldThrowServiceException_whenTagWasNotSaved() {
  //    // given
  //    when(giftCertificateRepository.getEntityBySpecification(any()))
  //        .thenReturn(Optional.ofNullable(firstExpectedGiftCertificateDto));
  //
  // when(giftCertificateRepository.update(any())).thenReturn(Optional.of(changedGiftCertificateDto));
  //    GiftCertificateDto incomingGiftCertificateDto =
  //        new GiftCertificateDto(firstExpectedGiftCertificateDto, Set.of(firstTag));
  //
  //    when(giftCertificateValidator.isValid(any())).thenReturn(true);
  //    when(builder.buildFromDto(any())).thenReturn(changedGiftCertificateDto);
  //    // when
  //    // then
  //    assertThrows(
  //        ServiceException.class,
  //        () -> giftCertificateService.update(ID_FOR_MANIPULATIONS, incomingGiftCertificateDto));
  //  }
  //
  //  @Test
  //  void testUpdate_ShouldReturnUpdatedGift_whenGiftIsExists() {
  //    // given
  //    when(giftCertificateRepository.getEntityBySpecification(any()))
  //        .thenReturn(Optional.ofNullable(firstExpectedGiftCertificateDto));
  //
  // when(giftCertificateRepository.update(any())).thenReturn(Optional.of(changedGiftCertificateDto));
  //    GiftCertificateDto incomingGiftCertificateDto =
  //        new GiftCertificateDto(firstExpectedGiftCertificateDto, Set.of(firstTag));
  //
  //    when(giftCertificateValidator.isValid(any())).thenReturn(true);
  //    when(builder.buildFromDto(any())).thenReturn(changedGiftCertificateDto);
  //    when(tagRepository.save(any())).thenReturn(Optional.of(firstTag));
  //    when(giftCertificateTagRepository.save(any())).thenReturn(Optional.of(giftCertificateTag));
  //
  //    when(tagRepository.getEntityListBySpecification(any()))
  //        .thenReturn(Collections.singletonList(firstTag));
  //    // when
  //    Optional<GiftCertificateDto> actualGiftCertificateDto =
  //        giftCertificateService.update(ID_FOR_MANIPULATIONS, incomingGiftCertificateDto);
  //    GiftCertificate actualGiftCertificate = buildFromDto(actualGiftCertificateDto.get());
  //    // then
  //    assertEquals(changedGiftCertificateDto, actualGiftCertificate);
  //  }
  //
  //  @Test
  //  void testDelete_shouldDeleteGift_whenGiftExistsInDatabase() {
  //    // given
  //    int expectedResult = 1;
  //    when(giftCertificateRepository.delete(ID_FOR_MANIPULATIONS)).thenReturn(expectedResult);
  //    when(giftCertificateRepository.getEntityBySpecification(any()))
  //        .thenReturn(Optional.of(firstExpectedGiftCertificateDto));
  //    // when
  //    int actualResult = giftCertificateService.delete(ID_FOR_MANIPULATIONS);
  //    // then
  //    assertEquals(expectedResult, actualResult);
  //  }
}
