package com.inoco.kata.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.inoco.kata.entity.User;
import com.inoco.kata.repository.UserRepository;
import com.inoco.kata.session.UserSession;

@CrossOrigin(origins = "http://localhost:4200")
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
		System.out.println("USER : " + userAuth);

		final Optional<User> optionalUser = this.userRepository.findAll().stream()
				.filter(user -> this.checkUser(user, userAuth)).findFirst();

		if (optionalUser.isPresent()) {
			final User currentUser = optionalUser.get();
			currentUser.setPassword(null);
			this.userSession.setCurrentUser(currentUser);
			return this.getUserSession();
		}

		return null;
	}

	@GetMapping("/logout")
	public void logout() {
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
