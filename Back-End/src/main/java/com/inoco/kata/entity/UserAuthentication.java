package com.inoco.kata.entity;

import javax.persistence.Entity;

@Entity
public class UserAuthentication {
	private String firstName;
	private String lastName;
	private String password;

	public UserAuthentication(final String firstName, final String lastName, final String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
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
