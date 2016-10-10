package com.ketan.ecom.db;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by ketansoni on 07/10/2016.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplicationBuilder springApplication = new SpringApplicationBuilder().web(false).sources(Application.class);
        springApplication.run(args);
    }
}
