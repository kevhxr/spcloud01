package com.hxr.eurekaserver1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerMain {
    static {
        System.setProperty("spring.profiles.active","peer1");
        //System.setProperty("spring.profiles.active","peer2");
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerMain.class, args);
    }
}
