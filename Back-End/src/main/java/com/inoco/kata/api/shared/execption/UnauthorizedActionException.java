package com.inoco.kata.api.shared.execption;

public class UnauthorizedActionException extends Exception {

	private static final long serialVersionUID = 8149115060772236859L;

	public UnauthorizedActionException() {
		super();
	}

	@Override
	public String getMessage() {
		return "You are not authorized to perform this action.";
	}
}
