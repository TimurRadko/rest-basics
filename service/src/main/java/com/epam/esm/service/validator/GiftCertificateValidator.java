package com.epam.esm.service.validator;

import com.epam.esm.service.dto.GiftCertificateDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GiftCertificateValidator extends AbstractValidator<GiftCertificateDto> {
  private static final int MIN_NAME_LENGTH = 3;
  private static final int MAX_NAME_LENGTH = 50;
  private static final double MIN_PRICE = 0.0d;
  private static final double MAX_PRICE = 5000d;
  private static final int MAX_DURATION = 365;

  @Override
  public boolean isValid(GiftCertificateDto giftCertificateDto) {
    setIsReturnValidTrue();
    eraseErrorMessages();

    if (giftCertificateDto == null) {
      addErrorMessage("To create a GiftCertificate you must send the GiftCertificate Entity");
      return false;
    }
    checkName(giftCertificateDto.getName());
    checkPrice(giftCertificateDto.getPrice());
    checkDuration(giftCertificateDto.getDuration());
    return isResultValid();
  }

  private void checkName(String name) {
    if (name == null || name.trim().length() == 0) {
      addErrorMessage("The GiftCertificate name is required");
      setIsResultValidFalse();
    } else if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
      addErrorMessage("The GiftCertificate name must be between 3 and 50 characters long");
      setIsResultValidFalse();
    }
  }

  private void checkPrice(BigDecimal price) {
    if (price == null) {
      addErrorMessage("The GiftCertificate price is required");
      setIsResultValidFalse();
    } else if ((price.compareTo(BigDecimal.valueOf(MIN_PRICE)) <= 0
        || price.compareTo(BigDecimal.valueOf(MAX_PRICE)) > 0)) {
      addErrorMessage("The GiftCertificate price must be more than 0.0 and less than 5000.0");
      setIsResultValidFalse();
    }
  }

  private void checkDuration(Integer duration) {
    if (duration == null) {
      addErrorMessage("The GiftCertificate duration is required");
      setIsResultValidFalse();
    } else if (duration < 0 || duration > MAX_DURATION) {
      addErrorMessage("The GiftCertificate duration must be more than 0 and less than 365");
      setIsResultValidFalse();
    }
  }
}
