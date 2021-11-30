package com.city.palabra.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("palabra.postgres.datasource")
public class PostgresProperties {
    String url;
    String username;
    String password;
    String platform;
    String driverClassName;
}
