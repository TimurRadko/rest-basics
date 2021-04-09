package com.epam.esm.service.validator;

import com.epam.esm.service.dto.GiftCertificateDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GiftCertificateValidator extends AbstractValidator<GiftCertificateDto> {
  private static final int MIN_NAME_LENGTH = 3;
  private static final int MAX_NAME_LENGTH = 50;
  private static final double MIN_PRICE = 0.0d;

  @Override
  public boolean isValid(GiftCertificateDto giftCertificateDto) {
    eraseErrorMessages();

    if (giftCertificateDto == null) {
      addErrorMessage("To create a Gift Certificate you must send the GiftCertificate Entity");
      return false;
    }
    checkName(giftCertificateDto);
    checkPrice(giftCertificateDto);
    checkDuration(giftCertificateDto);
    return isResultValid();
  }

  private void checkDuration(GiftCertificateDto giftCertificateDto) {
    Integer duration = giftCertificateDto.getDuration();
    if (duration != null && duration < 0) {
      addErrorMessage("To create a Gift Certificate the duration must be more than 0");
      setIsResultValidFalse();
    }
  }

  private void checkPrice(GiftCertificateDto giftCertificateDto) {
    BigDecimal price = giftCertificateDto.getPrice();
    if (price != null && price.compareTo(BigDecimal.valueOf(MIN_PRICE)) <= 0) {
      addErrorMessage("To create a Gift Certificate the price must be more than 0.0");
      setIsResultValidFalse();
    }
  }

  private void checkName(GiftCertificateDto giftCertificateDto) {
    String name = giftCertificateDto.getName();
    if (name == null || name.trim().length() == 0) {
      addErrorMessage("To create a Gift Certificate the name is required");
      setIsResultValidFalse();
    } else if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
      addErrorMessage(
          "To create a Gift certificate, the name must be between 3 and 50 characters long");
      setIsResultValidFalse();
    }
  }
}
