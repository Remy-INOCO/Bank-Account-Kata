package com.inoco.kata.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.inoco.kata.entity.User;
import com.inoco.kata.repository.UserRepository;

@Configuration
public class LoadDatabase {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(final UserRepository repository) {
		return args -> {
			LOGGER.info("Preloading {}", repository.save(new User("Bilbo", "sacquet", 1500, "Test123")));
			LOGGER.info("Preloading {}", repository.save(new User("Frodon", "sacquet", 1000, "Test123")));
			LOGGER.info("Preloading {}", repository.save(new User("Sam", "gamegie", 5500, "Test123")));
		};
	}
}
