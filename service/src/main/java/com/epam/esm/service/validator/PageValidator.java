package com.epam.esm.service.validator;

import com.epam.esm.service.dto.PageDto;
import org.springframework.stereotype.Component;

@Component
public class PageValidator extends AbstractValidator<PageDto> {

  @Override
  public boolean isValid(PageDto pageDto) {
    setIsReturnValidTrue();
    eraseErrorMessages();
    Integer page = pageDto.getPage();
    Integer size = pageDto.getSize();
    if (page == null) {
      addErrorMessage("The page mustn't be null");
      if (size == null) {
        addErrorMessage("The size mustn't be null");
      } else {
        checkSizeParameter(size);
      }
      return false;
    }
    if (size == null) {
      addErrorMessage("The size mustn't be null");
      checkPageParameter(page);
      return false;
    }
    checkPageParameter(page);
    checkSizeParameter(size);
    return isResultValid();
  }

  private void checkPageParameter(Integer page) {
    if (page <= 0) {
      addErrorMessage("The page must be more than 0");
      setIsResultValidFalse();
    }
  }

  private void checkSizeParameter(Integer size) {
    if (size < 0) {
      addErrorMessage("The size must be positive");
      setIsResultValidFalse();
    }
    if (size > 50) {
      addErrorMessage("The size must be less than 50");
      setIsResultValidFalse();
    }
  }
}
