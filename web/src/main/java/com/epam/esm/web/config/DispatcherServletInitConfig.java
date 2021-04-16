package com.epam.esm.web.config;

import com.epam.esm.service.config.ServiceConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class DispatcherServletInitConfig extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(ServiceConfig.class);
  }
}
