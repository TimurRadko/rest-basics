package com.epam.esm.service.validator;

import com.epam.esm.service.dto.UsersCreatingDto;
import com.epam.esm.service.locale.TranslatorLocale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidatorTest {
  private UserValidator validator;
  @Mock private TranslatorLocale translatorLocale;
  private static final String EXPECTED_NULL_MESSAGE =
      "To create a User you must send the required parameters (User Entity)";

  @BeforeEach
  void setUp() {
    validator = new UserValidator(translatorLocale);
  }

  @Test
  void testValidate_shouldReturnCorrectErrorMessage_whenUserIsNull() {
    assertFalse(validator.isValid(null));
    String actualErrorMessage = validator.getErrorMessage();
    assertEquals(EXPECTED_NULL_MESSAGE, actualErrorMessage);
  }

  @Test
  void testValidate_shouldReturnTrue_whenTagIsValid() {
    // given
    UsersCreatingDto userDto = new UsersCreatingDto("validName", "validPassword", "validPassword");
    // when
    boolean actualIsValid = validator.isValid(userDto);
    // then
    assertTrue(actualIsValid);
  }
}
