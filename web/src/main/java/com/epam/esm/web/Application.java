package com.epam.esm.web;

import com.epam.esm.service.config.ServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication(scanBasePackages = "com.epam.esm")
@Import(ServiceConfig.class)
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public ResourceBundleMessageSource getResourceBundleMessageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.addBasenames("errorMessage");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }
}
