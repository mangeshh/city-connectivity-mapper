package com.mc.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan({"com.mc.resource", "com.mc.config", "com.mc.main" })
public class Application {
    public static void main(String[] args) {
       SpringApplication.run(Application.class, args);
    }
}

