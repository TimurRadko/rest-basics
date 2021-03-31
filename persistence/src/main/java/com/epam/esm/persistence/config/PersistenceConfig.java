package com.epam.esm.persistence.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.epam.esm")
@EnableTransactionManagement
public class PersistenceConfig {
    private static final String HIKARI_PROPERTIES_PATH = "hikari.properties";

    @Bean
    public DataSource dataSource() throws IOException {
        Resource resource = new ClassPathResource(HIKARI_PROPERTIES_PATH);
        Properties hikariProperties = PropertiesLoaderUtils.loadProperties(resource);
        HikariConfig hikariConfig = new HikariConfig(hikariProperties);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws IOException {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DataSourceTransactionManager transactionManager() throws IOException {
        DataSource dataSource = dataSource();
        return new DataSourceTransactionManager(dataSource);
    }
}
