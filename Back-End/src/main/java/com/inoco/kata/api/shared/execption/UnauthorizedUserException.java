package com.inoco.kata.api.shared.execption;

public class UnauthorizedUserException extends Exception {

	private static final long serialVersionUID = 5498068440791053726L;

	public UnauthorizedUserException() {
		super();
	}

	@Override
	public String getMessage() {
		return "You are not authorized to access this content.";
	}
}
