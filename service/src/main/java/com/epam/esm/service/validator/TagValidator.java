package com.epam.esm.service.validator;

import com.epam.esm.dao.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagValidator extends AbstractValidator<Tag> {
  private static final int MIN_NAME_LENGTH = 3;
  private static final int MAX_NAME_LENGTH = 50;

  public boolean validate(Tag tag) {
    eraseErrorMessages();
    boolean isValid = true;
    if (tag == null) {
      addErrorMessage("To create a Tag you must send the Tag Entity");
      return false;
    }

    String name = tag.getName();
    if (name == null || name.trim().length() == 0) {
      addErrorMessage("To create a Tag the name is required");
      isValid = false;
    } else if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
      addErrorMessage("To create a Tag, the name must be between 3 and 50 characters long");
      isValid = false;
    }

    return isValid;
  }
}
