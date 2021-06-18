package com.epam.esm.service.validator;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class AbstractValidator<T> {
  private boolean isResultValid = true;
  private List<String> errorMessages = new ArrayList<>();

  public boolean isResultValid() {
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
}
