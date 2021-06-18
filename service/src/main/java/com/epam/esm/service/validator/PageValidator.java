package com.epam.esm.service.validator;

import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.locale.LocaleTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PageValidator extends AbstractValidator<PageDto> {
  private final LocaleTranslator localeTranslator;

  @Autowired
  public PageValidator(LocaleTranslator localeTranslator) {
    this.localeTranslator = localeTranslator;
  }

  @Override
  public boolean isValid(PageDto pageDto) {
    setIsReturnValidTrue();
    eraseErrorMessages();
    Integer page = pageDto.getPage();
    Integer size = pageDto.getSize();
    if (page == null) {
      addErrorMessage(localeTranslator.toLocale("exception.message.pageNotNull"));
      if (size == null) {
        addErrorMessage(localeTranslator.toLocale("exception.message.sizeNotNull"));
      } else {
        checkSizeParameter(size);
      }
      return false;
    }
    if (size == null) {
      addErrorMessage(localeTranslator.toLocale("exception.message.sizeNotNull"));
      checkPageParameter(page);
      return false;
    }
    checkPageParameter(page);
    checkSizeParameter(size);
    return isResultValid();
  }

  private void checkPageParameter(Integer page) {
    if (page <= 0) {
      addErrorMessage(localeTranslator.toLocale("exception.message.pageMoreThanZero"));
      setIsResultValidFalse();
    }
  }

  private void checkSizeParameter(Integer size) {
    if (size <= 0) {
      addErrorMessage(localeTranslator.toLocale("exception.message.sizeMoreThanZero"));
      setIsResultValidFalse();
    }
    if (size > 50) {
      addErrorMessage(localeTranslator.toLocale("exception.message.sizeLessThanFifty"));
      setIsResultValidFalse();
    }
  }
}
