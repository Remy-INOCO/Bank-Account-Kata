package com.inoco.kata.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.inoco.kata.entity.User;
import com.inoco.kata.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AutenticationController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AutenticationController.class);

	private final UserRepository repository;

	public AutenticationController(final UserRepository repository) {
		this.repository = repository;
	}

	@PostMapping("/login")
	public User getAuthentication(@RequestBody final User userAuth) throws Exception {
		System.out.println("USER : " + userAuth);

		final Optional<User> optionalUser = this.repository.findAll().stream()
				.filter(user -> this.checkUser(user, userAuth)).findFirst();

		if (optionalUser.isPresent()) {
			final User currentUser = optionalUser.get();
			currentUser.setPassword(null);
			return currentUser;
		}

		return null;
	}

	private boolean checkUser(final User user, final User userAuth) {
		return user.getFirstName().equals(userAuth.getFirstName()) && user.getLastName().equals(userAuth.getLastName())
				&& user.getPassword().equals(userAuth.getPassword());
	}
}
