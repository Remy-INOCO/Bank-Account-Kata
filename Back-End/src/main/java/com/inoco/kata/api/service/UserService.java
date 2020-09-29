package com.inoco.kata.api.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.inoco.kata.api.model.User;
import com.inoco.kata.api.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User saveUser(final User user) {
		return this.userRepository.save(user);
	}

	public Optional<User> getConnectedUser(final User userAuth) {
		return this.userRepository.findAll().stream().filter(user -> this.checkUser(user, userAuth)).findFirst();
	}

	private boolean checkUser(final User user, final User userAuth) {
		return user.getFirstName().equals(userAuth.getFirstName()) && user.getLastName().equals(userAuth.getLastName())
				&& user.getPassword().equals(userAuth.getPassword());
	}
}
