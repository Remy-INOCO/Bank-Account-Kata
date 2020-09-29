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
import com.inoco.kata.api.shared.CustomLoggerUtils;
import com.inoco.kata.api.shared.execption.UnauthorizedUserException;

@RestController
public class AuthenticationController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	private final UserRepository userRepository;

	private final UserSession userSession;

	public AuthenticationController(final UserRepository userRepository, final UserSession userSession) {
		this.userRepository = userRepository;
		this.userSession = userSession;
	}

	@PostMapping("/login")
	public User login(@RequestBody final User userAuth) throws UnauthorizedUserException {
		LOGGER.info("{} is trying to connect", CustomLoggerUtils.userInfos(userAuth));

		final Optional<User> optionalUser = this.userRepository.findAll().stream()
				.filter(user -> this.checkUser(user, userAuth)).findFirst();

		if (optionalUser.isPresent()) {
			final User currentUser = optionalUser.get();
			this.userSession.setCurrentUser(currentUser);

			LOGGER.info("{} is connected", CustomLoggerUtils.userInfos(currentUser));

			return this.getUserSession();
		}

		throw new UnauthorizedUserException();
	}

	@GetMapping("/logout")
	public boolean logout() {
		final User currentUser = this.getUserSession();

		if (currentUser != null) {
			LOGGER.info("{} is deconnected", CustomLoggerUtils.userInfos(currentUser));
			this.userSession.setCurrentUser(null);
			return true;
		}

		return false;
	}

	private User getUserSession() {
		return this.userSession.getCurrentUser();
	}

	private boolean checkUser(final User user, final User userAuth) {
		return user.getFirstName().equals(userAuth.getFirstName()) && user.getLastName().equals(userAuth.getLastName())
				&& user.getPassword().equals(userAuth.getPassword());
	}
}
