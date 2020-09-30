package com.inoco.kata.api.shared;

import com.inoco.kata.api.model.dto.UserDto;

public class CustomLoggerUtils {
	public CustomLoggerUtils() {
		super();
	}

	public static String userInfos(final UserDto currentUser) {
		return "User " + currentUser.getFirstName() + " " + currentUser.getLastName().toUpperCase();
	}
}
