package com.ketan.ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by ketansoni on 07/10/2016.
 */
@SpringBootApplication
@ComponentScan("com.ketan.ecom")
public class Application {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.run(args);
    }
}
