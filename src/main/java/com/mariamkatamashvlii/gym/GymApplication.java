package com.mariamkatamashvlii.gym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GymApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(GymApplication.class, args);
    }

}
