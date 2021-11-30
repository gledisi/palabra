package com.city.palabra.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter @Setter
@Configuration
@ConfigurationProperties("palabra.cassandra")
public class CassandraProperties {
    private String keyspaceName;
    private String username;
    private String password;
    private String port;
    private String contactPoints;
    private String datacenter;
}
