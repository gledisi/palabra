package com.city.palabra.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("palabra.security")
public class SecurityProperties {
   String allowedOrigins;
   String jwtSecret;
   Integer jwtValidFor;
   Integer smsJwtValidFor;
}
