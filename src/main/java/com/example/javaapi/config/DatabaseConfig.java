package com.example.javaapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class DatabaseConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        // In a real application, this would return the current user
        return () -> Optional.of("system");
    }
} 