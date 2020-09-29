package com.inoco.kata.api.service;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inoco.kata.api.model.Transaction;
import com.inoco.kata.api.model.dto.TransactionDto;
import com.inoco.kata.api.model.dto.UserDto;
import com.inoco.kata.api.repository.TransactionRepository;
import com.inoco.kata.api.shared.DateUtils;

@Service
public class TransactionService {
	private final TransactionRepository transactionRepository;

	@Autowired
	private ModelMapper modelMapper;

	public TransactionService(final TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public Transaction save(final TransactionDto transaction) {
		return this.transactionRepository.save(this.modelMapper.map(transaction, Transaction.class));
	}

	public List<TransactionDto> getTransactionsHistory(final UserDto currentUser) {
		final List<Transaction> transactionList = this
				.transactionsFilter(transaction -> this.checkUserId(transaction, currentUser.getId()));

		return this.modelMapper.map(transactionList, new TypeToken<List<TransactionDto>>() {
		}.getType());
	}

	public List<TransactionDto> getAccountStatements(final UserDto currentUser, final Date startDate,
			final Date endDate) {
		final List<Transaction> transactionList = this
				.transactionsFilter(transaction -> this.checkUserId(transaction, currentUser.getId())
						&& DateUtils.compareDate(transaction.getDate(), startDate, endDate));

		return this.modelMapper.map(transactionList, new TypeToken<List<TransactionDto>>() {
		}.getType());
	}

	private List<Transaction> transactionsFilter(final Predicate<? super Transaction> filtre) {
		return this.transactionRepository.findAll().stream().filter(filtre).collect(Collectors.toList());
	}

	private boolean checkUserId(final Transaction transaction, final Long userId) {
		return transaction.getIdUser().equals(userId);
	}
}
