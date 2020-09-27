package com.inoco.kata.api.configuration;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.inoco.kata.api.model.Operation;
import com.inoco.kata.api.model.Transaction;
import com.inoco.kata.api.model.User;
import com.inoco.kata.api.repository.TransactionRepository;
import com.inoco.kata.api.repository.UserRepository;

@Configuration
public class LoadDatabase {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(final UserRepository userRepository,
			final TransactionRepository transactionRepository) {
		return args -> {
			LOGGER.info("Preloading user {}", userRepository.save(new User("Bilbon", "Sacquet", 1500, "Test123")));
			LOGGER.info("Preloading user {}", userRepository.save(new User("Frodon", "Sacquet", 2000, "Test123")));
			LOGGER.info("Preloading user {}", userRepository.save(new User("Sam", "Gamegie", 2500, "Test123")));

			LOGGER.info("Preloading transaction {}",
					transactionRepository.save(new Transaction(this.getUserId(userRepository, 1), Operation.DEPOSIT,
							"Premier versement", new Date(), 1000, 1500)));
			LOGGER.info("Preloading transaction {}",
					transactionRepository.save(new Transaction(this.getUserId(userRepository, 2), Operation.DEPOSIT,
							"Premier versement", new Date(), 1000, 2000)));
			LOGGER.info("Preloading transaction {}",
					transactionRepository.save(new Transaction(this.getUserId(userRepository, 3), Operation.DEPOSIT,
							"Premier versement", new Date(), 1000, 2500)));
		};
	}

	private Long getUserId(final UserRepository userRepository, final int userId) {
		return userRepository.findById((long) userId).get().getId();
	}
}
