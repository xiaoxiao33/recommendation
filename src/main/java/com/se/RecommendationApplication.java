package com.se;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@SpringBootApplication
@ComponentScan(basePackages = {"com.se.service","com.se.repository","com.se.config","com.se.controller", "com.se.model"})
@EnableJpaAuditing
public class RecommendationApplication {
	public static void main(String[] args) {
		SpringApplication.run(RecommendationApplication.class, args);
	}
}