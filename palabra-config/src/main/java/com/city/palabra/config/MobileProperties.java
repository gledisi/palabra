package com.city.palabra.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("palabra.mobile.twilio.account")
public class MobileProperties {
    private String sid;
    private String token;
    private String number;
    private String smsText;
}
