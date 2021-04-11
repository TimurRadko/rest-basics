package com.epam.esm.web.config;

import com.epam.esm.dao.config.PersistenceConfig;
import com.epam.esm.service.config.ServiceConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;

public class DispatcherServletInitConfig
    extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected void registerDispatcherServlet(ServletContext servletContext) {
    servletContext.setInitParameter("spring.profiles.active", "dev");
    super.registerDispatcherServlet(servletContext);
  }

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class[0];
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[] {WebConfig.class, PersistenceConfig.class, ServiceConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/"};
  }
}
