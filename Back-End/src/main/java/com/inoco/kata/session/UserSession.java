package com.inoco.kata.session;

import org.springframework.stereotype.Component;

import com.inoco.kata.entity.User;

@Component
public class UserSession {
	private User currentUser;

	public UserSession() {
		super();
	}

	public UserSession(final User currentUser) {
		super();
		this.currentUser = currentUser;
	}

	public User getCurrentUser() {
		return this.currentUser;
	}

	public void setCurrentUser(final User currentUser) {
		this.currentUser = currentUser;
	}
}
