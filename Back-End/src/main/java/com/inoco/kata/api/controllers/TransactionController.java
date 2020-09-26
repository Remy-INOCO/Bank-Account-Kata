package com.inoco.kata.api.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public List<Transaction> getTransactionsHistory() {
		final User currentUser = this.getUserSession();

		LOGGER.info("{} check his transactions history", CustomLoggerUtils.userInfos(currentUser));
		return this.transactionRepository.findAll().parallelStream()
				.filter(transaction -> UserUtils.checkUserId(transaction, currentUser.getId()))
				.collect(Collectors.toList());
	}

	@PostMapping("/accountStatement/{startDate}-{endDate}")
	public List<Transaction> getAccountStatement(@PathVariable final Date startDate, @PathVariable final Date endDate) {
		final User currentUser = this.getUserSession();

		LOGGER.info("{} consults his account statement for period {} to {}", CustomLoggerUtils.userInfos(currentUser),
				startDate, endDate);
		return this.transactionRepository.findAll().parallelStream()
				.filter(transaction -> UserUtils.checkUserId(transaction, currentUser.getId())
						&& DateUtils.compareDate(transaction.getDate(), startDate, endDate))
				.collect(Collectors.toList());
	}

	@PutMapping("/deposit")
	public Transaction toMakeDeposit(@RequestBody final Transaction transaction) {
		final User currentUser = this.getUserSession();

		LOGGER.info("{} added {}€ to his balance", CustomLoggerUtils.userInfos(currentUser), transaction.getAmount());
		currentUser.setBalance(currentUser.getBalance() + transaction.getAmount());
		transaction.setIdUser(currentUser.getId());

		this.userRepository.save(currentUser);

		return this.transactionRepository.save(transaction);
	}

	@PutMapping("/withdrawal")
	public Transaction toMakeWithdrawal(@RequestBody final Transaction transaction) {
		final User currentUser = this.getUserSession();

		LOGGER.info("{} deduct {}€ from his balance", CustomLoggerUtils.userInfos(currentUser),
				transaction.getAmount());
		transaction.setIdUser(currentUser.getId());

		return this.transactionRepository.save(transaction);
	}

	private User getUserSession() {
		return this.userSession.getCurrentUser();
	}
}
