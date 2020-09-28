package com.inoco.kata.api.model;

import java.util.Date;

public class ErrorMessage {
	private int statusCode;
	private Date timestamp;
	private String message;

	public ErrorMessage(final int statusCode, final String message) {
		super();
		this.statusCode = statusCode;
		this.timestamp = new Date();
		this.message = message;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(final int statusCode) {
		this.statusCode = statusCode;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(final Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}
}
