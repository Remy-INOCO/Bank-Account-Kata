package com.inoco.kata.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.inoco.kata.api.model.dto.AuthenticationDto;
import com.inoco.kata.api.model.dto.UserDto;
import com.inoco.kata.api.service.UserService;
import com.inoco.kata.api.session.UserSession;
import com.inoco.kata.api.shared.CustomLoggerUtils;
import com.inoco.kata.api.shared.execption.UnauthorizedUserException;

@RestController
public class AuthenticationController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	private final UserService userService;

	private final UserSession userSession;

	public AuthenticationController(final UserService userService, final UserSession userSession) {
		this.userService = userService;
		this.userSession = userSession;
	}

	@PostMapping("/login")
	public UserDto login(@RequestBody final AuthenticationDto userAuth) throws UnauthorizedUserException {
		LOGGER.info("User {} {} is trying to connect", userAuth.getFirstName(), userAuth.getLastName().toUpperCase());

		final UserDto currentUser = this.userService.getConnectedUser(userAuth);

		if (currentUser != null) {
			LOGGER.info("{} is connected", CustomLoggerUtils.userInfos(currentUser));
			this.userSession.setCurrentUser(currentUser);
			return currentUser;
		}

		throw new UnauthorizedUserException();
	}

	@GetMapping("/logout")
	public boolean logout() {
		final UserDto currentUser = this.userSession.getCurrentUser();

		if (currentUser != null) {
			LOGGER.info("{} is deconnected", CustomLoggerUtils.userInfos(currentUser));
			this.userSession.setCurrentUser(null);
			return true;
		}

		return false;
	}
}
