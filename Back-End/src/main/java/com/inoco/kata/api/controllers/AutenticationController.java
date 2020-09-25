package com.inoco.kata.api.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.inoco.kata.api.model.User;
import com.inoco.kata.api.repository.UserRepository;
import com.inoco.kata.api.session.UserSession;

@RestController
public class AutenticationController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AutenticationController.class);

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final UserSession userSession;

	@Autowired
	public AutenticationController(final UserRepository userRepository, final UserSession userSession) {
		this.userRepository = userRepository;
		this.userSession = userSession;
	}

	@PostMapping("/login")
	public User login(@RequestBody final User userAuth) throws Exception {
		LOGGER.info("User {} is trying to connect", userAuth);

		final Optional<User> optionalUser = this.userRepository.findAll().stream()
				.filter(user -> this.checkUser(user, userAuth)).findFirst();

		if (optionalUser.isPresent()) {
			final User currentUser = optionalUser.get();
			this.userSession.setCurrentUser(currentUser);

			LOGGER.info("User {} is connected", currentUser);

			return this.getUserSession();
		}

		return null;
	}

	@GetMapping("/logout")
	public void logout() {
		LOGGER.info("User {} is connected", userAuth);

		this.userSession.setCurrentUser(null);
	}

	private User getUserSession() {
		return this.userSession.getCurrentUser();
	}

	private boolean checkUser(final User user, final User userAuth) {
		return user.getFirstName().equals(userAuth.getFirstName()) && user.getLastName().equals(userAuth.getLastName())
				&& user.getPassword().equals(userAuth.getPassword());
	}
}
