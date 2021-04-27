package com.epam.esm.service.validator;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GiftCertificatePriceValidator extends AbstractValidator<BigDecimal> {
  private static final double MIN_PRICE = 0.0d;
  private static final double MAX_PRICE = 5000d;

  @Override
  public boolean isValid(BigDecimal price) {
    setIsReturnValidTrue();
    eraseErrorMessages();

    if (price == null) {
      addErrorMessage(
          "To update price for Gift Certificate you must send the GiftCertificatePrice Entity");
      return false;
    }
    checkPrice(price);
    return isResultValid();
  }

  private void checkPrice(BigDecimal price) {
    if (price == null) {
      addErrorMessage("The price is required");
      setIsResultValidFalse();
    } else if ((price.compareTo(BigDecimal.valueOf(MIN_PRICE)) <= 0
        || price.compareTo(BigDecimal.valueOf(MAX_PRICE)) > 0)) {
      addErrorMessage("The price must be more than 0.0 and less than 5000.0");
      setIsResultValidFalse();
    }
  }
}
