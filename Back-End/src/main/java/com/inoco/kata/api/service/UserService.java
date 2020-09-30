package com.inoco.kata.api.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inoco.kata.api.model.User;
import com.inoco.kata.api.model.dto.UserDto;
import com.inoco.kata.api.repository.UserRepository;
import com.inoco.kata.api.session.UserSession;

@Service
public class UserService {
	private final UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	public UserService(final UserRepository userRepository, final UserSession userSession) {
		this.userRepository = userRepository;
	}

	public User save(final UserDto user) {
		return this.userRepository.save(this.modelMapper.map(user, User.class));
	}
}
