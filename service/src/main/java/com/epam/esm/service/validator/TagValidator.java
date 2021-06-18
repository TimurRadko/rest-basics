package com.epam.esm.service.validator;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.locale.LocaleTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagValidator extends AbstractValidator<TagDto> {
  private static final int MIN_NAME_LENGTH = 3;
  private static final int MAX_NAME_LENGTH = 50;
  private final LocaleTranslator localeTranslator;

  @Autowired
  public TagValidator(LocaleTranslator localeTranslator) {
    this.localeTranslator = localeTranslator;
  }

  public boolean isValid(TagDto tagDto) {
    setIsReturnValidTrue();
    eraseErrorMessages();

    if (tagDto == null) {
      addErrorMessage(localeTranslator.toLocale("exception.message.nullTag"));
      return false;
    }
    checkName(tagDto.getName());
    return isResultValid();
  }

  private void checkName(String name) {
    if (name == null || name.trim().length() == 0) {
      addErrorMessage(localeTranslator.toLocale("exception.message.nameTagRequired"));
      setIsResultValidFalse();
    } else if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
      addErrorMessage(localeTranslator.toLocale("exception.message.lengthTagName"));
      setIsResultValidFalse();
    }
  }
}
