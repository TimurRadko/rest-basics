package com.epam.esm.service.locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class ResourceBundleMessageSourceBean {

  @Bean
  public ResourceBundleMessageSource getResourceBundleMessageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.addBasenames("errorMessage");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }
}
