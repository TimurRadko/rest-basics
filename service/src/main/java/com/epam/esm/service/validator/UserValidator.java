package com.epam.esm.service.validator;

import com.epam.esm.service.dto.UserCredential;
import com.epam.esm.service.locale.LocaleTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator extends AbstractValidator<UserCredential> {
  private static final int MIN_NAME_LENGTH = 2;
  private static final int MIN_PASSWORD_LENGTH = 8;
  private static final int MAX_NAME_LENGTH = 50;
  private static final int MAX_PASSWORD_LENGTH = 100;
  private final LocaleTranslator localeTranslator;

  @Autowired
  public UserValidator(LocaleTranslator localeTranslator) {
    this.localeTranslator = localeTranslator;
  }

  @Override
  public boolean isValid(UserCredential userDto) {
    setIsReturnValidTrue();
    eraseErrorMessages();

    if (userDto == null) {
      addErrorMessage(localeTranslator.toLocale("exception.message.parameters"));
      return false;
    }
    checkLogin(userDto.getLogin());
    checkPassword(userDto.getPassword(), userDto.getConfirmPassword());
    return isResultValid();
  }

  private void checkPassword(String password, String confirmPassword) {
    if (password == null || password.trim().length() == 0) {
      addErrorMessage(localeTranslator.toLocale("exception.message.passwordRequired"));
      setIsResultValidFalse();
    } else if (confirmPassword == null || confirmPassword.trim().length() == 0) {
      addErrorMessage(localeTranslator.toLocale("exception.message.confirmPasswordRequired"));
      setIsResultValidFalse();
    } else if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
      addErrorMessage(localeTranslator.toLocale("exception.message.passwordLength"));
      setIsResultValidFalse();
    } else if (!password.equals(confirmPassword)) {
      addErrorMessage(localeTranslator.toLocale("exception.message.passwordEquals"));
      setIsResultValidFalse();
    }
  }

  private void checkLogin(String login) {
    if (login == null || login.trim().length() == 0) {
      addErrorMessage(localeTranslator.toLocale("exception.message.userNameRequired"));
      setIsResultValidFalse();
    } else if (login.length() < MIN_NAME_LENGTH || login.length() > MAX_NAME_LENGTH) {
      addErrorMessage(localeTranslator.toLocale("exception.message.userNameLength"));
      setIsResultValidFalse();
    }
  }
}
