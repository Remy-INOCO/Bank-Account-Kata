package com.inoco.kata.api.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inoco.kata.api.model.User;
import com.inoco.kata.api.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	public UserService(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User save(final User user) {
		return this.userRepository.save(user);
	}
}
