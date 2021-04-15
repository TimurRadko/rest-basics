package com.epam.esm.service.validator;

import com.epam.esm.service.dto.TagDto;
import org.springframework.stereotype.Component;

@Component
public class TagValidator extends AbstractValidator<TagDto> {

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
}
