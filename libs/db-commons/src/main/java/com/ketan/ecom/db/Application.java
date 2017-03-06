package com.ketan.ecom.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.ketan.ecom",
        "com.ketan.ecom.db",
        "com.ketan.ecom.**.db",
        "com.ketan.ecom.spring.configuration"})
//@EnableAutoConfiguration
public class Application {

    public static void main(String... args) {
        new SpringApplicationBuilder().web(false).sources(Application.class).run(args);
    }
}
