package com.goodpeople.setgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SetgoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SetgoApplication.class, args);
    }

}
