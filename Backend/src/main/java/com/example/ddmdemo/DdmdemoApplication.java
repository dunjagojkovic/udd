package com.example.ddmdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.ddmdemo.controller", "com.example.ddmdemo.configuration", "com.example.ddmdemo.service", "com.example.ddmdemo.repository"})

public class DdmdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DdmdemoApplication.class, args);
    }

}
