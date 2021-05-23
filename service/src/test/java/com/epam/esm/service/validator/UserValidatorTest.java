package com.epam.esm.service.validator;

import com.epam.esm.service.dto.UsersCreatingDto;
import com.epam.esm.service.locale.TranslatorLocale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserValidatorTest {
  @Mock private TranslatorLocale translatorLocale;
  @InjectMocks private UserValidator validator;
  private static final String EXPECTED_NULL_MESSAGE =
      "To create a User you must send the required parameters (User Entity)";

  @BeforeEach
  void setUp() {
    validator = new UserValidator(translatorLocale);
  }

  @Test
  void testValidate_shouldReturnCorrectErrorMessage_whenUserIsNull() {
    // given
    // when
    when(translatorLocale.toLocale(any())).thenReturn(EXPECTED_NULL_MESSAGE);
    assertFalse(validator.isValid(null));
    String actualErrorMessage = validator.getErrorMessage();
    // then
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
