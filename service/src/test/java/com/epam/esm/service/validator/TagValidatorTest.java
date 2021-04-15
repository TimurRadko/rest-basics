package com.epam.esm.service.validator;

import com.epam.esm.service.dto.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TagValidatorTest {
  private TagValidator validator;
  private static final String EXPECTED_NULL_TAG_MESSAGE =
      "To create a Tag you must send the Tag Entity";
  private static final String EXPECTED_NULL_OR_ZERO_LENGTH_NAME_TAG_MESSAGE =
      "The name is required";
  private static final String EXPECTED_MIN_OR_MAX_TAG_MESSAGE =
      "The name must be between 3 and 50 characters long";

  @BeforeEach
  void setUp() {
    validator = new TagValidator();
  }

  @Test
  void testValidate_shouldReturnCorrectErrorMessage_whenTagIsNull() {
    assertFalse(validator.isValid(null));
    String actualErrorMessage = validator.getErrorMessage();
    assertEquals(EXPECTED_NULL_TAG_MESSAGE, actualErrorMessage);
  }

  @Test
  void testValidate_shouldReturnTrue_whenTagIsValid() {
    // given
    TagDto tagDto = new TagDto(1L, "validName");
    // when
    boolean actualIsValid = validator.isValid(tagDto);
    // then
    assertTrue(actualIsValid);
  }

  @Test
  void testValidate_shouldReturnCorrectErrorMessage_whenTagNameIsNull() {
    // given
    TagDto tagDto = new TagDto(1L, null);
    // when
    boolean actualIsValid = validator.isValid(tagDto);
    String actualErrorMessage = validator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(EXPECTED_NULL_OR_ZERO_LENGTH_NAME_TAG_MESSAGE, actualErrorMessage);
  }

  @Test
  void testValidate_shouldReturnCorrectErrorMessage_whenTagNameLengthIsZero() {
    // given
    TagDto tagDto = new TagDto(1L, "");
    // when
    boolean actualIsValid = validator.isValid(tagDto);
    String actualErrorMessage = validator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(EXPECTED_NULL_OR_ZERO_LENGTH_NAME_TAG_MESSAGE, actualErrorMessage);
  }

  @Test
  void testValidate_shouldReturnCorrectErrorMessage_whenTagNameLengthIsLessThanMin() {
    // given
    TagDto tagDto = new TagDto(1L, "I");
    // when
    boolean actualIsValid = validator.isValid(tagDto);
    String actualErrorMessage = validator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(EXPECTED_MIN_OR_MAX_TAG_MESSAGE, actualErrorMessage);
  }

  @Test
  void testValidate_ShouldReturnCorrectErrorMessage_whenTagNameLengthIsMoreThanMax() {
    // given
    TagDto tagDto = new TagDto(1L, "nhomxlzywemguxgnthmsjqgdzdzxxgocafakaailmipargfpiby");
    // when
    boolean actualIsValid = validator.isValid(tagDto);
    String actualErrorMessage = validator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(EXPECTED_MIN_OR_MAX_TAG_MESSAGE, actualErrorMessage);
  }
}
