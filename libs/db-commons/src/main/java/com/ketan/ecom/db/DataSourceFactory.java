package com.ketan.ecom.db;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.PropertySourcesPropertyValues;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.sql.DataSource;

public class DataSourceFactory {
    @Autowired
    private ConfigurableEnvironment environment;

    @NotBlank
    private String url;

    @NotBlank
    private String driverClassName;

    @NotBlank
    private String username;
    private String password;

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DataSource dataSource(){
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();

        new RelaxedDataBinder(dataSource, "database.default").bind(new PropertySourcesPropertyValues(environment.getPropertySources()));
        return dataSource;

    }
}
