package com.inoco.kata.api.service;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inoco.kata.api.model.Transaction;
import com.inoco.kata.api.model.dto.TransactionDto;
import com.inoco.kata.api.model.dto.UserDto;
import com.inoco.kata.api.repository.TransactionRepository;
import com.inoco.kata.api.session.UserSession;
import com.inoco.kata.api.shared.CustomLoggerUtils;
import com.inoco.kata.api.shared.DateUtils;
import com.inoco.kata.api.shared.execption.CompareDateException;
import com.inoco.kata.api.shared.execption.UnauthorizedActionException;
import com.inoco.kata.api.shared.execption.UnauthorizedUserException;

@Service
public class TransactionService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
	private final TransactionRepository transactionRepository;
	private final UserService userService;
	private final UserSession userSession;

	@Autowired
	private ModelMapper modelMapper;

	public TransactionService(final TransactionRepository transactionRepository, final UserService userService,
			final UserSession userSession) {
		this.transactionRepository = transactionRepository;
		this.userService = userService;
		this.userSession = userSession;
	}

	public Transaction save(final TransactionDto transaction) {
		return this.transactionRepository.save(this.modelMapper.map(transaction, Transaction.class));
	}

	public List<TransactionDto> getTransactionsHistory() throws UnauthorizedUserException {
		final UserDto currentUser = this.getUserSession();
		LOGGER.info("{} check his transactions history", CustomLoggerUtils.userInfos(currentUser));

		final List<Transaction> transactionList = this
				.transactionsFilter(transaction -> this.checkUserId(transaction, currentUser.getId()));

		return this.modelMapper.map(transactionList, new TypeToken<List<TransactionDto>>() {
		}.getType());
	}

	public List<TransactionDto> getAccountStatements(final Date startDate, final Date endDate)
			throws CompareDateException, UnauthorizedUserException {
		final UserDto currentUser = this.getUserSession();

		if (!startDate.before(endDate)) {
			throw new CompareDateException();
		}

		startDate.setHours(0);
		startDate.setMinutes(0);
		endDate.setHours(23);
		endDate.setMinutes(59);
		LOGGER.info("{} consults his account statement for period {} to {}", CustomLoggerUtils.userInfos(currentUser),
				startDate, endDate);

		final List<Transaction> transactionList = this
				.transactionsFilter(transaction -> this.checkUserId(transaction, currentUser.getId())
						&& DateUtils.compareDate(transaction.getDate(), startDate, endDate));

		return this.modelMapper.map(transactionList, new TypeToken<List<TransactionDto>>() {
		}.getType());
	}

	public Transaction toMakeDeposit(final TransactionDto transaction) throws UnauthorizedUserException {
		final UserDto currentUser = this.getUserSession();
		final Integer balance = currentUser.getBalance() + transaction.getAmount();

		currentUser.setBalance(balance);
		transaction.setBalance(balance);
		transaction.setDate(new Date());
		transaction.setUserId(currentUser.getId());

		LOGGER.info("{} added {} € to his balance", CustomLoggerUtils.userInfos(currentUser), transaction.getAmount());

		this.userService.save(currentUser);

		return this.save(transaction);
	}

	public Transaction toMakeWithdrawal(final TransactionDto transaction)
			throws UnauthorizedUserException, UnauthorizedActionException {
		final UserDto currentUser = this.getUserSession();

		final Integer balance = currentUser.getBalance() - transaction.getAmount();

		if (balance < 0) {
			throw new UnauthorizedActionException();
		}

		currentUser.setBalance(balance);
		transaction.setBalance(balance);
		transaction.setDate(new Date());
		transaction.setUserId(currentUser.getId());

		LOGGER.info("{} deduct {} € from his balance", CustomLoggerUtils.userInfos(currentUser),
				transaction.getAmount());

		this.userService.save(currentUser);

		return this.save(transaction);
	}

	private List<Transaction> transactionsFilter(final Predicate<? super Transaction> filtre) {
		return this.transactionRepository.findAll().stream().filter(filtre).collect(Collectors.toList());
	}

	private boolean checkUserId(final Transaction transaction, final Long userId) {
		return transaction.getUserId().equals(userId);
	}

	private UserDto getUserSession() throws UnauthorizedUserException {
		final UserDto currentUser = this.userSession.getCurrentUser();

		if (currentUser != null) {
			return currentUser;
		}

		throw new UnauthorizedUserException();
	}
}
