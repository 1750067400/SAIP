package com.saip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.saip.entity")
@EnableJpaRepositories("com.saip.repository")
public class SaipApplication {
    public static void main(String[] args) {
        SpringApplication.run(SaipApplication.class, args);
    }
} 