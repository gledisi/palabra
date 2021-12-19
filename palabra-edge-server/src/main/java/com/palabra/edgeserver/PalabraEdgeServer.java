package com.palabra.edgeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PalabraEdgeServer {

    public static void main(String[] args) {
        SpringApplication.run(PalabraEdgeServer.class, args);
    }

}
