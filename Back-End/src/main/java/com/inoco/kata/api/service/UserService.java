package com.inoco.kata.api.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inoco.kata.api.model.User;
import com.inoco.kata.api.model.dto.AuthenticationDto;
import com.inoco.kata.api.model.dto.UserDto;
import com.inoco.kata.api.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	public UserService(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User save(final UserDto user) {
		return this.userRepository.save(this.modelMapper.map(user, User.class));
	}

	public UserDto getConnectedUser(final AuthenticationDto userAuth) {
		final Optional<User> optionalUser = this.userRepository.findAll().stream()
				.filter(user -> this.checkUser(user, userAuth)).findFirst();

		return optionalUser.isPresent() ? this.modelMapper.map(optionalUser.get(), UserDto.class) : null;
	}

	private boolean checkUser(final User user, final AuthenticationDto userAuth) {
		return user.getFirstName().equals(userAuth.getFirstName()) && user.getLastName().equals(userAuth.getLastName())
				&& user.getPassword().equals(userAuth.getPassword());
	}
}
