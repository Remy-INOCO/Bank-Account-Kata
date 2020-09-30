package com.inoco.kata.api.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inoco.kata.api.model.User;
import com.inoco.kata.api.model.dto.AuthenticationDto;
import com.inoco.kata.api.model.dto.UserDto;
import com.inoco.kata.api.repository.UserRepository;
import com.inoco.kata.api.session.UserSession;
import com.inoco.kata.api.shared.CustomLoggerUtils;
import com.inoco.kata.api.shared.execption.UnauthorizedUserException;

@Service
public class AuthenticationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);
	private final UserRepository userRepository;
	private final UserSession userSession;

	@Autowired
	private ModelMapper modelMapper;

	public AuthenticationService(final UserRepository userRepository, final UserSession userSession) {
		this.userRepository = userRepository;
		this.userSession = userSession;
	}

	public UserDto login(final AuthenticationDto userAuth) throws UnauthorizedUserException {
		LOGGER.info("User {} {} is trying to connect", userAuth.getFirstName(), userAuth.getLastName().toUpperCase());

		final Optional<User> optionalUser = this.userRepository.findAll().stream()
				.filter(user -> this.checkUser(user, userAuth)).findFirst();

		if (optionalUser.isPresent()) {
			final UserDto currentUser = this.modelMapper.map(optionalUser.get(), UserDto.class);

			LOGGER.info("{} is connected", CustomLoggerUtils.userInfos(currentUser));
			this.userSession.setCurrentUser(currentUser);
			return currentUser;
		}

		throw new UnauthorizedUserException();
	}

	public boolean logout() {
		final UserDto currentUser = this.userSession.getCurrentUser();

		if (currentUser != null) {
			LOGGER.info("{} is deconnected", CustomLoggerUtils.userInfos(currentUser));
			this.userSession.setCurrentUser(null);
			return true;
		}

		return false;
	}

	private boolean checkUser(final User user, final AuthenticationDto userAuth) {
		return user.getFirstName().equals(userAuth.getFirstName()) && user.getLastName().equals(userAuth.getLastName())
				&& user.getPassword().equals(userAuth.getPassword());
	}
}
