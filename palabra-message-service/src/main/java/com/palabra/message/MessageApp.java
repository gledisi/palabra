package com.palabra.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.palabra")
public class MessageApp {

    public static void main(String[] args) {
        SpringApplication.run(MessageApp.class, args);
    }

}
