package com.mariamkatamashvlii.gym;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Generated
public class GymApplication {
    public static void main(String[] args) throws SecurityException {
        SpringApplication.run(GymApplication.class, args);
    }
}