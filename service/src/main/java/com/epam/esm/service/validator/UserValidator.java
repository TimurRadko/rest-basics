package com.epam.esm.service.validator;

import com.epam.esm.service.dto.UsersCreatingDto;
import org.springframework.stereotype.Component;

@Component
public class UserValidator extends AbstractValidator<UsersCreatingDto> {
  private static final int MIN_NAME_LENGTH = 2;
  private static final int MIN_PASSWORD_LENGTH = 8;
  private static final int MAX_LENGTH = 50;

  @Override
  public boolean isValid(UsersCreatingDto userDto) {
    setIsReturnValidTrue();
    eraseErrorMessages();

    if (userDto == null) {
      addErrorMessage("To create a User you must send the required parameters (User Entity)");
      return false;
    }
    checkLogin(userDto.getLogin());
    checkPassword(userDto.getPassword(), userDto.getConfirmPassword());
    return isResultValid();
  }

  private void checkPassword(String password, String confirmPassword) {
    if (password == null || password.trim().length() == 0) {
      addErrorMessage("To create User password is required");
      setIsResultValidFalse();
    } else if (confirmPassword == null || confirmPassword.trim().length() == 0) {
      addErrorMessage("To create User confirmPassword is required");
      setIsResultValidFalse();
    } else if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_LENGTH) {
      addErrorMessage("To create User password must be between 8 and 50 characters long");
      setIsResultValidFalse();
    } else if (!password.equals(confirmPassword)) {
      addErrorMessage("To create User password and confirmPassword must be complete equals");
      setIsResultValidFalse();
    }
  }

  private void checkLogin(String login) {
    if (login == null || login.trim().length() == 0) {
      addErrorMessage("The User name is required");
      setIsResultValidFalse();
    } else if (login.length() < MIN_NAME_LENGTH || login.length() > MAX_LENGTH) {
      addErrorMessage("The User name must be between 2 and 50 characters long");
      setIsResultValidFalse();
    }
  }
}
