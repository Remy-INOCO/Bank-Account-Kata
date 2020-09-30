package com.inoco.kata.api.session;

import org.springframework.stereotype.Component;

import com.inoco.kata.api.model.dto.UserDto;

@Component
public class UserSession {
	private UserDto currentUser;

	public UserSession() {
		super();
	}

	public UserDto getCurrentUser() {
		return this.currentUser;
	}

	public void setCurrentUser(final UserDto user) {
		this.currentUser = user;
	}
}
