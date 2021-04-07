package com.epam.esm.service.validator;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class AbstractValidator<T> {
  private List<String> errorMessages = new ArrayList<>();

  public String getErrorMessage() {
    return String.join(". ", errorMessages);
  }

  void addErrorMessage(String errorMessage) {
    errorMessages.add(errorMessage);
  }

  void eraseErrorMessages() {
    errorMessages = new ArrayList<>();
  }

  public abstract boolean validate(T t);
}
