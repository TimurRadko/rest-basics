package com.epam.esm.dao.config;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Profile("test")
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.epam.esm")
public class TestDaoConfig {

  @Bean
  @Profile("test")
  public DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .addScript("classpath:creating-tables-scripts.sql")
        .addScript("classpath:inserting-data-scripts.sql")
        .build();
  }

  @Bean
  @Profile("test")
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource());
  }

  @Bean
  @Profile("test")
  public DataSourceTransactionManager transactionManager() {
    DataSource dataSource = dataSource();
    return new DataSourceTransactionManager(dataSource);
  }
}
