package com.city.palabra.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.nio.file.FileSystem;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties
@Slf4j
public class PropertiesConfiguration {

    @Bean
    public PropertySourcesPlaceholderConfigurer properties(Environment env) {
        final PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        ppc.setIgnoreResourceNotFound(true);
        ppc.setLocations(resources(env.getProperty("filePath.config")));
        return ppc;
    }

    private String basePath(Environment env) {
        return env.getProperty("filePath.config");
    }

    private Resource[] resources(String basePath) {
        String basePathPlusSeparator = basePath+File.separatorChar;
        log.info("Base path======================================={}",basePath);
        final List<Resource> resourceList = new ArrayList<>();
        resourceList.add(new FileSystemResource(basePathPlusSeparator  + "kafka-consumer.properties"));
        resourceList.add(new FileSystemResource(basePathPlusSeparator + "cassandra.properties"));
        resourceList.add(new FileSystemResource(basePathPlusSeparator + "postgres.properties"));
        resourceList.add(new FileSystemResource(basePathPlusSeparator + "security.properties"));
        resourceList.add(new FileSystemResource(basePathPlusSeparator + "mobile.properties"));
        return resourceList.toArray(new Resource[0]);
    }
}
