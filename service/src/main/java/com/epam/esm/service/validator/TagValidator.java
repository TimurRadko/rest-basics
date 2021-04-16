package com.epam.esm.service.validator;

import com.epam.esm.service.dto.TagDto;
import org.springframework.stereotype.Component;

@Component
public class TagValidator extends AbstractValidator<TagDto> {
  private static final int MIN_NAME_LENGTH = 3;
  private static final int MAX_NAME_LENGTH = 50;

  public boolean isValid(TagDto tagDto) {
    setIsReturnValidTrue();
    eraseErrorMessages();

    if (tagDto == null) {
      addErrorMessage("To create a Tag you must send the Tag Entity");
      return false;
    }
    checkName(tagDto.getName());
    return isResultValid();
  }

  private void checkName(String name) {
    if (name == null || name.trim().length() == 0) {
      addErrorMessage("The name is required");
      setIsResultValidFalse();
    } else if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
      addErrorMessage("The name must be between 3 and 50 characters long");
      setIsResultValidFalse();
    }
  }
}
