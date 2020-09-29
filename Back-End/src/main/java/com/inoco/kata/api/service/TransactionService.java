package com.inoco.kata.api.service;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.inoco.kata.api.model.Transaction;
import com.inoco.kata.api.model.User;
import com.inoco.kata.api.repository.TransactionRepository;
import com.inoco.kata.api.shared.DateUtils;

@Service
public class TransactionService {
	private final TransactionRepository transactionRepository;

	public TransactionService(final TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public Transaction saveTransaction(final Transaction transaction) {
		return this.transactionRepository.save(transaction);
	}

	public List<Transaction> getTransactionsHistory(final User currentUser) {
		return this.transactionsFilter(transaction -> this.checkUserId(transaction, currentUser.getId()));
	}

	public List<Transaction> getAccountStatements(final User currentUser, final Date startDate, final Date endDate) {
		return this.transactionsFilter(transaction -> this.checkUserId(transaction, currentUser.getId())
				&& DateUtils.compareDate(transaction.getDate(), startDate, endDate));
	}

	private List<Transaction> transactionsFilter(final Predicate<? super Transaction> filtre) {
		return this.transactionRepository.findAll().stream().filter(filtre).collect(Collectors.toList());
	}

	private boolean checkUserId(final Transaction transaction, final Long userId) {
		return transaction.getIdUser().equals(userId);
	}
}
