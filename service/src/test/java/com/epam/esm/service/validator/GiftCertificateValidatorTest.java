package com.epam.esm.service.validator;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.service.dto.GiftCertificateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GiftCertificateValidatorTest {
    private GiftCertificateValidator validator;
    private final GiftCertificate giftCertificate = new GiftCertificate(
            1L,
            "validName",
            "validDescription",
            BigDecimal.valueOf(12),
            14,
            LocalDateTime.now(),
            LocalDateTime.now());
    private final Set<Tag> tags = Set.of(new Tag(1L, "test1"));
    private static final String EXPECTED_NULL_GIFT_CERTIFICATE_MESSAGE = "To create a Gift Certificate " +
            "you must send the GiftCertificate Entity";
    private static final String EXPECTED_NULL_OR_ZERO_LENGTH_NAME_GIFT_CERTIFICATE_MESSAGE =
            "To create a Gift Certificate the name is required";
    private static final String EXPECTED_MIN_OR_MAX_GIFT_CERTIFICATE_MESSAGE = "To create a Gift certificate, " +
            "the name must be between 3 and 50 characters long";
    private static final String EXPECTED_NEGATIVE_PRICE_GIFT_CERTIFICATE_MESSAGE = "To create a Gift " +
            "Certificate the price must be more than 0.0";
    private static final String EXPECTED_NEGATIVE_DURATION_GIFT_CERTIFICATE_MESSAGE = "To create " +
            "a Gift Certificate the duration must be more than 0";

    @BeforeEach
    void setUp() {
        validator = new GiftCertificateValidator();
    }

    @Test
    void testValidateShouldReturnFalseAndCorrectErrorMessageWhenGiftCertificateIsNull() {
        assertFalse(validator.validate(null));
        String actualErrorMessage = validator.getErrorMessage();
        assertEquals(EXPECTED_NULL_GIFT_CERTIFICATE_MESSAGE, actualErrorMessage);
    }

    @Test
    void testValidateShouldReturnTrueWhenGiftCertificateIsValid() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
        assertTrue(validator.validate(giftCertificateDto));
    }

    @Test
    void testValidateShouldReturnFalseAndCorrectErrorMessageWhenGiftCertificateNameIsNull() {
        giftCertificate.setName(null);
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
        assertFalse(validator.validate(giftCertificateDto));
        String actualErrorMessage = validator.getErrorMessage();
        assertEquals(EXPECTED_NULL_OR_ZERO_LENGTH_NAME_GIFT_CERTIFICATE_MESSAGE, actualErrorMessage);
    }

    @Test
    void testValidateShouldReturnFalseAndCorrectErrorMessageWhenGiftCertificateNameLengthIsZero() {
        giftCertificate.setName("");
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
        assertFalse(validator.validate(giftCertificateDto));
        String actualErrorMessage = validator.getErrorMessage();
        assertEquals(EXPECTED_NULL_OR_ZERO_LENGTH_NAME_GIFT_CERTIFICATE_MESSAGE, actualErrorMessage);
    }

    @Test
    void testValidateShouldReturnFalseAndCorrectErrorMessageWhenGiftCertificateNameLengthIsLessThanMin() {
        giftCertificate.setName("I");
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
        assertFalse(validator.validate(giftCertificateDto));
        String actualErrorMessage = validator.getErrorMessage();
        assertEquals(EXPECTED_MIN_OR_MAX_GIFT_CERTIFICATE_MESSAGE, actualErrorMessage);
    }

    @Test
    void testValidateShouldReturnFalseAndCorrectErrorMessageWhenGiftCertificateNameLengthIsMoreThanMax() {
        giftCertificate.setName("nhomxlzywemguxgnthmsjqgdzdzxxgocafakaailmipargfpiby");
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
        assertFalse(validator.validate(giftCertificateDto));
        String actualErrorMessage = validator.getErrorMessage();
        assertEquals(EXPECTED_MIN_OR_MAX_GIFT_CERTIFICATE_MESSAGE, actualErrorMessage);
    }

    @Test
    void testValidateShouldReturnFalseAndCorrectErrorMessageWhenGiftCertificatePriceLessThanZero() {
        giftCertificate.setPrice(BigDecimal.valueOf(-1));
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
        assertFalse(validator.validate(giftCertificateDto));
        String actualErrorMessage = validator.getErrorMessage();
        assertEquals(EXPECTED_NEGATIVE_PRICE_GIFT_CERTIFICATE_MESSAGE, actualErrorMessage);
    }

    @Test
    void testValidateShouldReturnFalseAndCorrectErrorMessageWhenGiftCertificateDurationLessThanZero() {
        giftCertificate.setDuration(-1);
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
        boolean b = validator.validate(giftCertificateDto);
        assertFalse(b);
        String actualErrorMessage = validator.getErrorMessage();
        assertEquals(EXPECTED_NEGATIVE_DURATION_GIFT_CERTIFICATE_MESSAGE, actualErrorMessage);
    }

}
