package com.ketan.ecom.db;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties
@PropertySource(value = {"classpath:/database-default.properties", "classpath:/database.properties"})
public class Database {

    private static final String READ_PREFIX = "database.read";
    private static final String WRITE_PREFIX = "database.write";
    private static final String URL = "url";

    @Bean
    @ConfigurationProperties(prefix = READ_PREFIX)
    @ConditionalOnProperty(prefix = READ_PREFIX, name = URL)
    public DataSourceFactory readProperties() {
        return new DataSourceFactory();
    }

    @Bean
    @ConfigurationProperties(prefix = WRITE_PREFIX)
    @ConditionalOnProperty(prefix = WRITE_PREFIX, name = URL)
    public DataSourceFactory writeProperties() {
        return new DataSourceFactory();
    }

    @Bean
    @ConfigurationProperties(prefix = READ_PREFIX)
    @Primary
    @ConditionalOnProperty(prefix = READ_PREFIX, name = URL)
    public DataSource readDataSource() {
        return readProperties().dataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = WRITE_PREFIX)
    @ConditionalOnProperty(prefix = WRITE_PREFIX, name = URL)
    public DataSource writeDataSource() {
        return writeProperties().dataSource();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = READ_PREFIX)
    @ConditionalOnProperty(prefix = READ_PREFIX, name = URL)
    public EcomLiquibase readDatabaseUpdater() {
        EcomLiquibase liquibase = new EcomLiquibase();
        liquibase.setDataSource(readDataSource());
        return liquibase;
    }

    @Bean
    @ConfigurationProperties(prefix = WRITE_PREFIX)
    @ConditionalOnProperty(prefix = WRITE_PREFIX, name = URL)
    public EcomLiquibase writeDatabaseUpdater() {
        EcomLiquibase liquibase = new EcomLiquibase();
        liquibase.setDataSource(writeDataSource());
        return liquibase;
    }

}
