package com.city.message.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "com.city.message.repository")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.keyspace}")
    private String keyspaceName;
    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"com.city"};
    }

    @Override
    protected String getKeyspaceName() {
        return this.keyspaceName;
    }
}
