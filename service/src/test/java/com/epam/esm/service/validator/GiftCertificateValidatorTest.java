package com.epam.esm.service.validator;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GiftCertificateValidatorTest {
  private GiftCertificateValidator validator;
  private static final long ID = 1L;
  private static final String NAME = "Valid Name";
  private static final String DESCRIPTION = "Valid Description";
  private static final BigDecimal PRICE = BigDecimal.valueOf(12);
  private static final int DURATION = 14;
  private static final LocalDateTime NOW = LocalDateTime.now();
  private final Set<TagDto> tagDtos = Set.of(new TagDto(1L, "tag"));

  private static final String NULL_GIFT_CERTIFICATE_MESSAGE =
      "To create a GiftCertificate you must send the GiftCertificate Entity";
  private static final String NULL_OR_ZERO_LENGTH_NAME_GIFT_CERTIFICATE_MESSAGE =
      "The GiftCertificate name is required";
  private static final String INCORRECT_LENGTH_GIFT_CERTIFICATE_MESSAGE =
      "The GiftCertificate name must be between 3 and 50 characters long";
  private static final String NEGATIVE_PRICE_GIFT_CERTIFICATE_MESSAGE =
      "The GiftCertificate price must be more than 0.0 and less than 5000.0";
  private static final String NEGATIVE_DURATION_GIFT_CERTIFICATE_MESSAGE =
      "The GiftCertificate duration must be more than 0 and less than 365";
  private static final String INCORRECT_LENGTH_AND_NEGATIVE_PRICE_GIFT_CERTIFICATE_MESSAGE =
      "The GiftCertificate name must be between 3 and 50 characters long\n"
          + "The GiftCertificate price must be more than 0.0 and less than 5000.0";
  private static final String INCORRECT_LENGTH_AND_NEGATIVE_DURATION_GIFT_CERTIFICATE_MESSAGE =
      "The GiftCertificate name must be between 3 and 50 characters long\n"
          + "The GiftCertificate duration must be more than 0 and less than 365";
  private static final String INCORRECT_ALL_DATA_GIFT_CERTIFICATE_MESSAGE =
      "The GiftCertificate name must be between 3 and 50 characters long\n"
          + "The GiftCertificate price must be more than 0.0 and less than 5000.0\n"
          + "The GiftCertificate duration must be more than 0 and less than 365";

  @BeforeEach
  void setUp() {
    validator = new GiftCertificateValidator();
  }

  @Test
  void testValidate_shouldReturnCorrectErrorMessage_whenGiftCertificateIsNull() {
    // given
    // when
    boolean actualIsValid = validator.isValid(null);
    String actualErrorMessage = validator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(NULL_GIFT_CERTIFICATE_MESSAGE, actualErrorMessage);
  }

  @Test
  void testValidate_shouldReturnTrue_whenGiftCertificateIsValid() {
    // given
    GiftCertificateDto giftCertificateDto =
        new GiftCertificateDto(ID, NAME, DESCRIPTION, PRICE, DURATION, NOW, NOW, tagDtos);
    // when
    boolean actualIsValid = validator.isValid(giftCertificateDto);
    // then
    assertTrue(actualIsValid);
  }

  @Test
  void testValidate_shouldReturnCorrectErrorMessage_whenGiftsNameIsNull() {
    // given
    GiftCertificateDto giftCertificateDto =
        new GiftCertificateDto(ID, null, DESCRIPTION, PRICE, DURATION, NOW, NOW, tagDtos);
    // when
    boolean actualIsValid = validator.isValid(giftCertificateDto);
    String actualErrorMessage = validator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(NULL_OR_ZERO_LENGTH_NAME_GIFT_CERTIFICATE_MESSAGE, actualErrorMessage);
  }

  @Test
  void testValidate_shouldReturnCorrectErrorMessage_whenGiftsNameLengthIsZero() {
    // given
    GiftCertificateDto giftCertificateDto =
        new GiftCertificateDto(ID, "", DESCRIPTION, PRICE, DURATION, NOW, NOW, tagDtos);
    // when
    boolean actualIsValid = validator.isValid(giftCertificateDto);
    String actualErrorMessage = validator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(NULL_OR_ZERO_LENGTH_NAME_GIFT_CERTIFICATE_MESSAGE, actualErrorMessage);
  }

  @Test
  void testValidate_shouldReturnCorrectErrorMessage_whenGiftNameLengthIsLessThanMin() {
    // given
    GiftCertificateDto giftCertificateDto =
        new GiftCertificateDto(ID, "I", DESCRIPTION, PRICE, DURATION, NOW, NOW, tagDtos);
    // when
    boolean actualIsValid = validator.isValid(giftCertificateDto);
    String actualErrorMessage = validator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(INCORRECT_LENGTH_GIFT_CERTIFICATE_MESSAGE, actualErrorMessage);
  }

  @Test
  void testValidate_shouldReturnCorrectErrorMessage_whenGiftNameLengthIsMoreThanMax() {
    // given
    GiftCertificateDto giftCertificateDto =
        new GiftCertificateDto(
            ID,
            "nhomxlzywemguxgnthmsjqgdzdzxxgocafakaailmipargfpiby",
            DESCRIPTION,
            PRICE,
            DURATION,
            NOW,
            NOW,
            tagDtos);
    // when
    boolean actualIsValid = validator.isValid(giftCertificateDto);
    String actualErrorMessage = validator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(INCORRECT_LENGTH_GIFT_CERTIFICATE_MESSAGE, actualErrorMessage);
  }

  @Test
  void testValidate_shouldReturnCorrectErrorMessage_whenGiftPriceLessThanZero() {
    // given
    GiftCertificateDto giftCertificateDto =
        new GiftCertificateDto(
            ID, NAME, DESCRIPTION, BigDecimal.valueOf(-1), DURATION, NOW, NOW, tagDtos);
    // when
    boolean actualIsValid = validator.isValid(giftCertificateDto);
    String actualErrorMessage = validator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(NEGATIVE_PRICE_GIFT_CERTIFICATE_MESSAGE, actualErrorMessage);
  }

  @Test
  void testValidate_shouldReturnCorrectErrorMessage_whenGiftDurationLessThanZero() {
    // given
    GiftCertificateDto giftCertificateDto =
        new GiftCertificateDto(ID, NAME, DESCRIPTION, PRICE, -1, NOW, NOW, tagDtos);
    // when
    boolean actualIsValid = validator.isValid(giftCertificateDto);
    String actualErrorMessage = validator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(NEGATIVE_DURATION_GIFT_CERTIFICATE_MESSAGE, actualErrorMessage);
  }

  @Test
  void testValidate_shouldReturnCorrectErrorMessage_whenGiftNameLengthAndPriceIncorrect() {
    // given
    GiftCertificateDto giftCertificateDto =
        new GiftCertificateDto(
            ID,
            "nhomxlzywemguxgnthmsjqgdzdzxxgocafakaailmipargfpiby",
            DESCRIPTION,
            BigDecimal.valueOf(-1),
            DURATION,
            NOW,
            NOW,
            tagDtos);
    // when
    boolean actualIsValid = validator.isValid(giftCertificateDto);
    String actualErrorMessage = validator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(INCORRECT_LENGTH_AND_NEGATIVE_PRICE_GIFT_CERTIFICATE_MESSAGE, actualErrorMessage);
  }

  @Test
  void testValidate_shouldReturnCorrectErrorMessage_whenGiftNameLengthAndDurationIncorrect() {
    // given
    GiftCertificateDto giftCertificateDto =
        new GiftCertificateDto(
            ID,
            "nhomxlzywemguxgnthmsjqgdzdzxxgocafakaailmipargfpiby",
            DESCRIPTION,
            PRICE,
            -1,
            NOW,
            NOW,
            tagDtos);
    // when
    boolean actualIsValid = validator.isValid(giftCertificateDto);
    String actualErrorMessage = validator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(
        INCORRECT_LENGTH_AND_NEGATIVE_DURATION_GIFT_CERTIFICATE_MESSAGE, actualErrorMessage);
  }

  @Test
  void testValidate_shouldReturnCorrectErrorMessage_whenAllDataIncorrect() {
    // given
    GiftCertificateDto giftCertificateDto =
        new GiftCertificateDto(
            ID,
            "nhomxlzywemguxgnthmsjqgdzdzxxgocafakaailmipargfpiby",
            DESCRIPTION,
            BigDecimal.valueOf(-1),
            -1,
            NOW,
            NOW,
            tagDtos);
    // when
    boolean actualIsValid = validator.isValid(giftCertificateDto);
    String actualErrorMessage = validator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(INCORRECT_ALL_DATA_GIFT_CERTIFICATE_MESSAGE, actualErrorMessage);
  }
}
