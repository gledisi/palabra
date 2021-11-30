package com.city.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.city")
public class MessageApp {

    public static void main(String[] args) {
        SpringApplication.run(MessageApp.class, args);
    }

}
