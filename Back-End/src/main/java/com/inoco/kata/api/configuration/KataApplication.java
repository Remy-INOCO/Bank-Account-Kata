package com.inoco.kata.api.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.inoco.kata.api.model")
@EnableJpaRepositories("com.inoco.kata.api.repository")
@ComponentScan(basePackages = { "com.inoco.kata.api" })
public class KataApplication {

	public static void main(final String[] args) {
		SpringApplication.run(KataApplication.class, args);
	}
}
