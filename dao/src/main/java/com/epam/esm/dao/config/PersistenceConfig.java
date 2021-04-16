package com.epam.esm.dao.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Profile("dev")
@Configuration
@ComponentScan(basePackages = "com.epam.esm")
@EnableTransactionManagement
public class PersistenceConfig {}
