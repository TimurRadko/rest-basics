package com.epam.esm.service.validator;

import com.epam.esm.persistence.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TagValidatorTest {
    private TagValidator validator;
    private static final String EXPECTED_NULL_TAG_MESSAGE = "To create a Tag you must send the Tag Entity";
    private static final String EXPECTED_NULL_OR_ZERO_LENGTH_NAME_TAG_MESSAGE = "To create a Tag the name is required";
    private static final String EXPECTED_MIN_OR_MAX_TAG_MESSAGE = "To create a Tag, the name must be " +
            "between 3 and 50 characters long";

    private Tag tag;

    @BeforeEach
    void setUp() {
        validator = new TagValidator();
        tag = new Tag(1L, "test1");
    }

    @Test
    void testValidateShouldReturnFalseAndCorrectErrorMessageWhenTagIsNull() {
        assertFalse(validator.validate(null));
        String actualErrorMessage = validator.getErrorMessage();
        assertEquals(EXPECTED_NULL_TAG_MESSAGE, actualErrorMessage);
    }

    @Test
    void testValidateShouldReturnTrueWhenTagIsValid() {
        assertTrue(validator.validate(tag));
    }

    @Test
    void testValidateShouldReturnFalseAndCorrectErrorMessageWhenTagNameIsNull() {
        tag.setName(null);
        assertFalse(validator.validate(tag));
        String actualErrorMessage = validator.getErrorMessage();
        assertEquals(EXPECTED_NULL_OR_ZERO_LENGTH_NAME_TAG_MESSAGE, actualErrorMessage);
    }

    @Test
    void testValidateShouldReturnFalseAndCorrectErrorMessageWhenTagNameLengthIsZero() {
        tag.setName("");
        assertFalse(validator.validate(tag));
        String actualErrorMessage = validator.getErrorMessage();
        assertEquals(EXPECTED_NULL_OR_ZERO_LENGTH_NAME_TAG_MESSAGE, actualErrorMessage);
    }

    @Test
    void testValidateShouldReturnFalseAndCorrectErrorMessageWhenTagNameLengthIsLessThanMin() {
        tag.setName("I");
        assertFalse(validator.validate(tag));
        String actualErrorMessage = validator.getErrorMessage();
        assertEquals(EXPECTED_MIN_OR_MAX_TAG_MESSAGE, actualErrorMessage);
    }

    @Test
    void testValidateShouldReturnFalseAndCorrectErrorMessageWhenTagNameLengthIsMoreThanMax() {
        tag.setName("nhomxlzywemguxgnthmsjqgdzdzxxgocafakaailmipargfpiby");
        assertFalse(validator.validate(tag));
        String actualErrorMessage = validator.getErrorMessage();
        assertEquals(EXPECTED_MIN_OR_MAX_TAG_MESSAGE, actualErrorMessage);
    }
}
