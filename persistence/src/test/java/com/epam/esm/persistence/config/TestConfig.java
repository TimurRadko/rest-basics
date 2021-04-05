package com.epam.esm.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


public class TestConfig {
    @Bean
    @Profile("test")
    public DataSource getTestDataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:creating-tables-scripts.sql")
                .addScript("classpath:filling-tables-scripts.sql")
                .build();
    }

    @Bean
    @Profile("test")
    public JdbcTemplate h2JdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
