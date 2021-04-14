package com.epam.esm.service.validator;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.dto.GiftCertificateDto;
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

  private final GiftCertificate giftCertificate =
      new GiftCertificate(
          1L,
          "validName",
          "validDescription",
          BigDecimal.valueOf(12),
          14,
          LocalDateTime.now(),
          LocalDateTime.now());
  private final Set<Tag> tags = Set.of(new Tag(1L, "tag"));

  private static final String NULL_GIFT_CERTIFICATE_MESSAGE =
      "To create a Gift Certificate " + "you must send the GiftCertificate Entity";
  private static final String NULL_OR_ZERO_LENGTH_NAME_GIFT_CERTIFICATE_MESSAGE =
      "To create a Gift Certificate the name is required";
  private static final String INCORRECT_LENGTH_GIFT_CERTIFICATE_MESSAGE =
      "To create a Gift certificate, " + "the name must be between 3 and 50 characters long";
  private static final String NEGATIVE_PRICE_GIFT_CERTIFICATE_MESSAGE =
      "To create a Gift " + "Certificate the price must be more than 0.0 and less than 5000.0";
  private static final String NEGATIVE_DURATION_GIFT_CERTIFICATE_MESSAGE =
      "To create " + "a Gift Certificate the duration must be more than 0 and less than 365";
  private static final String INCORRECT_LENGTH_AND_NEGATIVE_PRICE_GIFT_CERTIFICATE_MESSAGE =
      "To create a Gift certificate, "
          + "the name must be between 3 and 50 characters long. To create a Gift "
          + "Certificate the price must be more than 0.0 and less than 5000.0";
  private static final String INCORRECT_LENGTH_AND_NEGATIVE_DURATION_GIFT_CERTIFICATE_MESSAGE =
      "To create a Gift certificate, "
          + "the name must be between 3 and 50 characters long. To create "
          + "a Gift Certificate the duration must be more than 0 and less than 365";
  private static final String INCORRECT_ALL_DATA_GIFT_CERTIFICATE_MESSAGE =
      "To create a Gift certificate, "
          + "the name must be between 3 and 50 characters long. To create a Gift "
          + "Certificate the price must be more than 0.0 and less than 5000.0. To create "
          + "a Gift Certificate the duration must be more than 0 and less than 365";

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
    GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
    // when
    boolean actualIsValid = validator.isValid(giftCertificateDto);
    // then
    assertTrue(actualIsValid);
  }

  @Test
  void testValidate_shouldReturnCorrectErrorMessage_whenGiftsNameIsNull() {
    // given
    giftCertificate.setName(null);
    GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
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
    giftCertificate.setName("");
    GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
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
    giftCertificate.setName("I");
    GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
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
    giftCertificate.setName("nhomxlzywemguxgnthmsjqgdzdzxxgocafakaailmipargfpiby");
    GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
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
    giftCertificate.setPrice(BigDecimal.valueOf(-1));
    GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
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
    giftCertificate.setDuration(-1);
    GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
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
    giftCertificate.setName("nhomxlzywemguxgnthmsjqgdzdzxxgocafakaailmipargfpiby");
    giftCertificate.setPrice(BigDecimal.valueOf(-1));
    GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
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
    giftCertificate.setName("nhomxlzywemguxgnthmsjqgdzdzxxgocafakaailmipargfpiby");
    giftCertificate.setDuration(-1);
    GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
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
    giftCertificate.setName("nhomxlzywemguxgnthmsjqgdzdzxxgocafakaailmipargfpiby");
    giftCertificate.setPrice(BigDecimal.valueOf(-1));
    giftCertificate.setDuration(-1);
    GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
    // when
    boolean actualIsValid = validator.isValid(giftCertificateDto);
    String actualErrorMessage = validator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(INCORRECT_ALL_DATA_GIFT_CERTIFICATE_MESSAGE, actualErrorMessage);
  }
}
