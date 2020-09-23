package com.inoco.kata.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.inoco.kata.entity.User;
import com.inoco.kata.entity.UserAuthentication;
import com.inoco.kata.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	private final UserRepository repository;

	public UserController(final UserRepository repository) {
		this.repository = repository;
	}

	@PostMapping("/authentication")
	public User getAuthentication(@RequestBody final User userAuth) throws Exception {
//		final User userAuth = new User(user.getFirstName(), user.getLastName(),
//				);
//		userAuth.setFirstName(firstName);
//		userAuth.setLastName(firstName);
//		userAuth.setPassword(firstName);
		System.out.println("USER : " + userAuth);

		this.repository.findAll().stream().filter(user -> );

//		return this.repository.findOne(Example.of(userAuth))
//				.orElseThrow(() -> new Exception("Error during authentication for user " + user));

	}

	private boolean checkUser(final User user, final User userAuth) {
		return user.getFirstName().equals(userAuth.getFirstName()) && user.getLastName().equals(userAuth.getLastName())
				&& user.getPassword().equals(userAuth.getPassword());
	}
}
