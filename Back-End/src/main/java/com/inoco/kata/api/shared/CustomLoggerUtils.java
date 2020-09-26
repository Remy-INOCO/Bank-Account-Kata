package com.inoco.kata.api.shared;

import com.inoco.kata.api.model.User;

public class CustomLoggerUtils {
	public CustomLoggerUtils() {
		super();
	}

	public static String userInfos(final User user) {
		return "User " + user.getFirstName() + " " + user.getLastName().toUpperCase();
	}
}
