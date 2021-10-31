package com.city.palabra.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties
public class PropertiesConfiguration {

    @Bean
    public PropertySourcesPlaceholderConfigurer properties(Environment env) {
        final PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        ppc.setIgnoreResourceNotFound(true);
        ppc.setLocations(resources(basePath(env)));
        return ppc;
    }

    private String basePath(Environment env) {
        return env.getProperty("filePath.config");
    }

    private Resource[] resources(String basePath) {
        final List<Resource> resourceList = new ArrayList<>();
        resourceList.add(new FileSystemResource(basePath + "persistence.properties"));
        resourceList.add(new FileSystemResource(basePath + "scheduler.properties"));
        resourceList.add(new FileSystemResource(basePath + "kafka-consumer.properties"));
        return resourceList.toArray(new Resource[0]);
    }
}
