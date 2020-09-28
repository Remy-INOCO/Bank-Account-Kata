package com.inoco.kata.api.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inoco.kata.api.model.Transaction;
import com.inoco.kata.api.model.User;
import com.inoco.kata.api.repository.TransactionRepository;
import com.inoco.kata.api.repository.UserRepository;
import com.inoco.kata.api.session.UserSession;
import com.inoco.kata.api.shared.CustomLoggerUtils;
import com.inoco.kata.api.shared.DateUtils;
import com.inoco.kata.api.shared.UserUtils;
import com.inoco.kata.api.shared.execption.CompareDateException;
import com.inoco.kata.api.shared.execption.UnauthorizedActionException;
import com.inoco.kata.api.shared.execption.UnauthorizedUserException;

@RequestMapping("/transactions")
@RestController
public class TransactionController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	private final TransactionRepository transactionRepository;

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final UserSession userSession;

	@Autowired
	public TransactionController(final TransactionRepository transactionRepository, final UserRepository userRepository,
			final UserSession userSession) {
		this.transactionRepository = transactionRepository;
		this.userRepository = userRepository;
		this.userSession = userSession;
	}

	@GetMapping("/history")
	public List<Transaction> getTransactionsHistory() throws UnauthorizedUserException {
		final User currentUser = this.getUserSession();

		LOGGER.info("{} check his transactions history", CustomLoggerUtils.userInfos(currentUser));
		return this.transactionRepository.findAll().stream()
				.filter(transaction -> UserUtils.checkUserId(transaction, currentUser.getId()))
				.collect(Collectors.toList());
	}

	@GetMapping("/accountStatement/{startDate}-{endDate}")
	public List<Transaction> getAccountStatement(@PathVariable final Date startDate, @PathVariable final Date endDate)
			throws UnauthorizedUserException, CompareDateException {
		final User currentUser = this.getUserSession();

		if (!startDate.before(endDate)) {
			throw new CompareDateException();
		}

		startDate.setHours(0);
		startDate.setMinutes(0);
		endDate.setHours(23);
		endDate.setMinutes(59);
		LOGGER.info("{} consults his account statement for period {} to {}", CustomLoggerUtils.userInfos(currentUser),
				startDate, endDate);
		return this.transactionRepository.findAll().stream()
				.filter(transaction -> UserUtils.checkUserId(transaction, currentUser.getId())
						&& DateUtils.compareDate(transaction.getDate(), startDate, endDate))
				.collect(Collectors.toList());
	}

	@PutMapping("/deposit")
	public Transaction toMakeDeposit(@RequestBody final Transaction transaction) throws UnauthorizedUserException {
		final User currentUser = this.getUserSession();

		final Integer balance = currentUser.getBalance() + transaction.getAmount();
		currentUser.setBalance(balance);
		transaction.setBalance(balance);
		transaction.setDate(new Date());
		transaction.setIdUser(currentUser.getId());

		LOGGER.info("{} added {} € to his balance", CustomLoggerUtils.userInfos(currentUser), transaction.getAmount());

		this.userRepository.save(currentUser);

		return this.transactionRepository.save(transaction);
	}

	@PutMapping("/withdrawal")
	public Transaction toMakeWithdrawal(@RequestBody final Transaction transaction)
			throws UnauthorizedUserException, UnauthorizedActionException {
		final User currentUser = this.getUserSession();

		final Integer balance = currentUser.getBalance() - transaction.getAmount();

		if (balance < 0) {
			throw new UnauthorizedActionException();
		}

		currentUser.setBalance(balance);
		transaction.setBalance(balance);
		transaction.setDate(new Date());
		transaction.setIdUser(currentUser.getId());

		LOGGER.info("{} deduct {} € from his balance", CustomLoggerUtils.userInfos(currentUser),
				transaction.getAmount());

		this.userRepository.save(currentUser);

		return this.transactionRepository.save(transaction);
	}

	private User getUserSession() throws UnauthorizedUserException {
		final User currentUser = this.userSession.getCurrentUser();

		if (currentUser != null) {
			return currentUser;
		}

		throw new UnauthorizedUserException();
	}
}
