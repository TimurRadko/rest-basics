package com.epam.esm.dao.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.epam.esm")
@EnableTransactionManagement
@EntityScan(basePackages = "com.epam.esm.dao.entity")
public class PersistenceConfig {}
