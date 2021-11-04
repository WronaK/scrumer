package com.example.scrumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableJpaAuditing
@EnableMongoRepositories(basePackages = "com.example.scrumer.chat.repository.mongo")
@EnableJpaRepositories(basePackages = {"com.example.scrumer.chat.repository.jpa", "com.example.scrumer.project.repository","com.example.scrumer.team.repository", "com.example.scrumer.task.repository", "com.example.scrumer.user.repository"})
@SpringBootApplication
public class ScrumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrumerApplication.class, args);
    }

}
