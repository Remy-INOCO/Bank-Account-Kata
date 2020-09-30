package com.inoco.kata.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import javax.persistence.*;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private Integer balance;
	private String password;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY)
	private List<Transaction> transactionList;

	public User() {
		super();
	}

	public User(final String firstName, final String lastName, final Integer balance, final String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.balance = balance;
		this.password = password;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
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

	public Integer getBalance() {
		return this.balance;
	}

	public void setBalance(final Integer balance) {
		this.balance = balance;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public List<Transaction> getTransactionList() {
		return this.transactionList;
	}

	public void setTransactionList(final List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + this.id + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName
				+ '\'' + ", balance='" + this.balance + '\'' + ", password='" + this.password + '\'' + '}';
	}
}
