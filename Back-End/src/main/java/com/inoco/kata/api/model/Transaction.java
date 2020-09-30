package com.inoco.kata.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	private Long userId;

	@Enumerated(EnumType.STRING)
	@Column(name = "operation")
	private Operation operation;

	@Column(name = "wording")
	private String wording;

	@Column(name = "date")
	private Date date;

	@Column(name = "amount")
	private int amount;

	@Column(name = "balance")
	private int balance;

	public Transaction() {
		super();
	}

	public Transaction(final Long idUser, final Operation operation, final String wording, final Date date,
			final int amount, final int balance) {
		super();
		this.userId = idUser;
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
		return "Transaction{" + "id=" + this.id + ", idUser=" + this.userId + ", operation='" + this.operation + '\''
				+ ", wording='" + this.wording + '\'' + ", date='" + this.date + '\'' + ", amount='" + this.amount
				+ '\'' + ", balance=" + this.balance + '}';
	}
}
