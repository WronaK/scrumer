package com.example.scrumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ScrumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrumerApplication.class, args);
    }

}
