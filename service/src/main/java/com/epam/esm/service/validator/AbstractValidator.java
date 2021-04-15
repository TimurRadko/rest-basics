package com.epam.esm.service.validator;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class AbstractValidator<T> {
  private static final int MIN_NAME_LENGTH = 3;
  private static final int MAX_NAME_LENGTH = 50;
  private boolean isResultValid = true;
  private List<String> errorMessages = new ArrayList<>();

  boolean isResultValid() {
    return isResultValid;
  }

  void setIsResultValidFalse() {
    isResultValid = false;
  }

  void setIsReturnValidTrue() {
    isResultValid = true;
  }

  public String getErrorMessage() {
    return String.join("\n", errorMessages);
  }

  void addErrorMessage(String errorMessage) {
    errorMessages.add(errorMessage);
  }

  void eraseErrorMessages() {
    errorMessages = new ArrayList<>();
  }

  public abstract boolean isValid(T t);

  void checkName(String name) {
    if (name == null || name.trim().length() == 0) {
      addErrorMessage("The name is required");
      setIsResultValidFalse();
    } else if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
      addErrorMessage("The name must be between 3 and 50 characters long");
      setIsResultValidFalse();
    }
  }
}
