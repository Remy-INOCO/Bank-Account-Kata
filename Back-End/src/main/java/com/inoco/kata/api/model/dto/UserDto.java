package com.inoco.kata.api.model.dto;

import java.util.List;

public class UserDto {
	private Long id;
	private String firstName;
	private String lastName;
	private Integer balance;
	private List<TransactionDto> transactionList;

	public UserDto() {
		super();
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

	public List<TransactionDto> getTransactionList() {
		return this.transactionList;
	}

	public void setTransactionList(final List<TransactionDto> transactionList) {
		this.transactionList = transactionList;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + this.id + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName
				+ '\'' + ", balance='" + this.balance + '\'' + ", transactionList='" + this.transactionList + '\''
				+ '}';
	}
}
