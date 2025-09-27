package org.example.creditprocessing.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:credit-config.properties")
@ConfigurationProperties(prefix = "credit")
public class CreditConfig {
}