package com.inoco.kata.api.session;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inoco.kata.api.model.User;

@Component
public class UserSession {
	private User currentUser;

	@Autowired
	private ModelMapper modelMapper;

	public UserSession() {
		super();
	}

	public User getCurrentUser() {
		return this.currentUser;
	}

	public void setCurrentUser(final User user) {
		this.currentUser = user;
	}
}
