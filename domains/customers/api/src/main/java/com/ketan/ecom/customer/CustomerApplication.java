package com.ketan.ecom.customer;

import org.axonframework.contextsupport.spring.AnnotationDriven;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.ketan.ecom")
@AnnotationDriven
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(CustomerApplication.class);
        springApplication.run(args);
    }
}
