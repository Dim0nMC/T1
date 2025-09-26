package org.example.creditprocessing.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.math.BigDecimal;

@Configuration
@PropertySource("classpath:credit-config.properties")
@ConfigurationProperties(prefix = "credit")
public class CreditConfig {
}