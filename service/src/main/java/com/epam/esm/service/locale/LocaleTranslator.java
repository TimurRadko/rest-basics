package com.epam.esm.service.locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class LocaleTranslator {
  private final ResourceBundleMessageSource messageSource;

  @Autowired
  public LocaleTranslator(ResourceBundleMessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public String toLocale(String code) {
    Locale locale = LocaleContextHolder.getLocale();
    return messageSource.getMessage(code, null, locale);
  }
}
