package com.inoco.kata.api.shared.execption;

public class CompareDateException extends Exception {

	private static final long serialVersionUID = 9184189160184047117L;

	public CompareDateException() {
		super();
	}

	@Override
	public String getMessage() {
		return "The start date can't be later to or equal than the end date.";
	}
}
