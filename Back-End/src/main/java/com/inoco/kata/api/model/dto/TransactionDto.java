package com.inoco.kata.api.model.dto;

import java.util.Date;

import com.inoco.kata.api.model.Operation;

public class TransactionDto {
	private Long id;
	private Long userId;
	private Operation operation;
	private String wording;
	private Date date;
	private int amount;
	private int balance;

	public TransactionDto() {
		super();
	}

	public TransactionDto(final Long userId, final Operation operation, final String wording, final Date date,
			final int amount, final int balance) {
		super();
		this.userId = userId;
		this.operation = operation;
		this.wording = wording;
		this.date = date;
		this.amount = amount;
		this.balance = balance;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	public Operation getOperation() {
		return this.operation;
	}

	public void setOperation(final Operation operation) {
		this.operation = operation;
	}

	public String getWording() {
		return this.wording;
	}

	public void setWording(final String wording) {
		this.wording = wording;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(final int amount) {
		this.amount = amount;
	}

	public int getBalance() {
		return this.balance;
	}

	public void setBalance(final int balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Transaction{" + "id=" + this.id + ", userId=" + this.userId + ", operation='" + this.operation + '\''
				+ ", wording='" + this.wording + '\'' + ", date='" + this.date + '\'' + ", amount='" + this.amount
				+ '\'' + ", balance=" + this.balance + '}';
	}
}
