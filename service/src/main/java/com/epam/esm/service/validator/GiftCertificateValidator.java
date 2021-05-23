package com.epam.esm.service.validator;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.locale.TranslatorLocale;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GiftCertificateValidator extends AbstractValidator<GiftCertificateDto> {
  private static final int MIN_NAME_LENGTH = 3;
  private static final int MAX_NAME_LENGTH = 50;
  private static final double MIN_PRICE = 0.0d;
  private static final double MAX_PRICE = 5000d;
  private static final int MAX_DURATION = 365;
  private final TranslatorLocale translatorLocale;

  public GiftCertificateValidator(TranslatorLocale translatorLocale) {
    this.translatorLocale = translatorLocale;
  }

  @Override
  public boolean isValid(GiftCertificateDto giftCertificateDto) {
    setIsReturnValidTrue();
    eraseErrorMessages();

    if (giftCertificateDto == null) {
      addErrorMessage(translatorLocale.toLocale("exception.message.nullCertificate"));
      return false;
    }
    checkName(giftCertificateDto.getName());
    checkPrice(giftCertificateDto.getPrice());
    checkDuration(giftCertificateDto.getDuration());
    return isResultValid();
  }

  private void checkName(String name) {
    if (name == null || name.trim().length() == 0) {
      addErrorMessage(translatorLocale.toLocale("exception.message.nameRequired"));
      setIsResultValidFalse();
    } else if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
      addErrorMessage(translatorLocale.toLocale("exception.message.lengthName"));
      setIsResultValidFalse();
    }
  }

  private void checkPrice(BigDecimal price) {
    if (price == null) {
      addErrorMessage(translatorLocale.toLocale("exception.message.priceRequired"));
      setIsResultValidFalse();
    } else if ((price.compareTo(BigDecimal.valueOf(MIN_PRICE)) <= 0
        || price.compareTo(BigDecimal.valueOf(MAX_PRICE)) > 0)) {
      addErrorMessage(translatorLocale.toLocale("exception.message.priceValue"));
      setIsResultValidFalse();
    }
  }

  private void checkDuration(Integer duration) {
    if (duration == null) {
      addErrorMessage(translatorLocale.toLocale("exception.message.durationRequired"));
      setIsResultValidFalse();
    } else if (duration < 0 || duration > MAX_DURATION) {
      addErrorMessage(translatorLocale.toLocale("exception.message.durationValue"));
      setIsResultValidFalse();
    }
  }
}
