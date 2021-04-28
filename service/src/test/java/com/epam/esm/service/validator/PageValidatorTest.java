package com.epam.esm.service.validator;

import com.epam.esm.service.dto.PageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PageValidatorTest {
  private PageValidator pageValidator;
  private static final String NULL_PAGE_MESSAGE = "The page mustn't be null";
  private static final String NULL_SIZE_MESSAGE = "The size mustn't be null";
  private static final String NULL_PAGE_AND_SIZE_MESSAGE =
      "The page mustn't be null\nThe size mustn't be null";
  private static final String LESS_THAN_ZERO_PAGE_MESSAGE = "The page must be more than 0";
  private static final String LESS_THAN_ZERO_SIZE_MESSAGE = "The page must be positive";
  private PageDto pageDto;

  @BeforeEach
  void setUp() {
    pageDto = new PageDto(1, 1);
    pageValidator = new PageValidator();
  }

  @Test
  void testsValid_shouldReturnCorrectErrorMessage_whenPageIsNull() {
    // given
    pageDto.setPage(null);
    // when
    boolean actualIsValid = pageValidator.isValid(pageDto);
    String actualErrorMessage = pageValidator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(NULL_PAGE_MESSAGE, actualErrorMessage);
  }

  @Test
  void testsValid_shouldReturnCorrectErrorMessage_whenSizeIsNull() {
    // given
    pageDto.setSize(null);
    // when
    boolean actualIsValid = pageValidator.isValid(pageDto);
    String actualErrorMessage = pageValidator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(NULL_SIZE_MESSAGE, actualErrorMessage);
  }

  @Test
  void testsValid_shouldReturnCorrectErrorMessage_whenPageAndSizeIsNull() {
    // given
    pageDto.setPage(null);
    pageDto.setSize(null);
    // when
    boolean actualIsValid = pageValidator.isValid(pageDto);
    String actualErrorMessage = pageValidator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(NULL_PAGE_AND_SIZE_MESSAGE, actualErrorMessage);
  }

  @Test
  void testsValid_shouldReturnCorrectErrorMessage_whenPageIsLessThanZero() {
    // given
    pageDto.setPage(-1);
    // when
    boolean actualIsValid = pageValidator.isValid(pageDto);
    String actualErrorMessage = pageValidator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(LESS_THAN_ZERO_PAGE_MESSAGE, actualErrorMessage);
  }

  @Test
  void testsValid_shouldReturnCorrectErrorMessage_whenSizeIsLessThanZero() {
    // given
    pageDto.setSize(-1);
    // when
    boolean actualIsValid = pageValidator.isValid(pageDto);
    String actualErrorMessage = pageValidator.getErrorMessage();
    // then
    assertFalse(actualIsValid);
    assertEquals(LESS_THAN_ZERO_SIZE_MESSAGE, actualErrorMessage);
  }
}
