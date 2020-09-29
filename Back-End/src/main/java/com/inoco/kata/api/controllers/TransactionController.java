package com.inoco.kata.api.controllers;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inoco.kata.api.model.Transaction;
import com.inoco.kata.api.model.User;
import com.inoco.kata.api.service.TransactionService;
import com.inoco.kata.api.service.UserService;
import com.inoco.kata.api.session.UserSession;
import com.inoco.kata.api.shared.CustomLoggerUtils;
import com.inoco.kata.api.shared.execption.CompareDateException;
import com.inoco.kata.api.shared.execption.UnauthorizedActionException;
import com.inoco.kata.api.shared.execption.UnauthorizedUserException;

@RequestMapping("/transactions")
@RestController
public class TransactionController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

	private final TransactionService transactionService;

	private final UserService userService;

	private final UserSession userSession;

	public TransactionController(final TransactionService transactionService, final UserService userService,
			final UserSession userSession) {
		this.transactionService = transactionService;
		this.userService = userService;
		this.userSession = userSession;
	}

	@GetMapping("/history")
	public List<Transaction> getTransactionsHistory() throws UnauthorizedUserException {
		final User currentUser = this.getUserSession();

		LOGGER.info("{} check his transactions history", CustomLoggerUtils.userInfos(currentUser));
		return this.transactionService.getTransactionsHistory(currentUser);
	}

	@GetMapping("/accountStatement/{startDate}-{endDate}")
	public List<Transaction> getAccountStatements(@PathVariable final Date startDate, @PathVariable final Date endDate)
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

		return this.transactionService.getAccountStatements(currentUser, startDate, endDate);
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

		this.userService.saveUser(currentUser);

		return this.transactionService.saveTransaction(transaction);
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

		this.userService.saveUser(currentUser);

		return this.transactionService.saveTransaction(transaction);
	}

	private User getUserSession() throws UnauthorizedUserException {
		final User currentUser = this.userSession.getCurrentUser();

		if (currentUser != null) {
			return currentUser;
		}

		throw new UnauthorizedUserException();
	}
}
