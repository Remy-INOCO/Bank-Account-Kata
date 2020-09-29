package com.inoco.kata.api.model.dto;

public class AuthenticationDto {
	private String firstName;
	private String lastName;
	private String password;

	public AuthenticationDto() {
		super();
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User{" + "firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName + '\'' + ", password='"
				+ this.password + '\'' + '}';
	}
}
