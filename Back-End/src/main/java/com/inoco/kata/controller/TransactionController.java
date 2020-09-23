package com.inoco.kata.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inoco.kata.entity.Transaction;
import com.inoco.kata.entity.User;
import com.inoco.kata.repository.TransactionRepository;
import com.inoco.kata.repository.UserRepository;
import com.inoco.kata.session.UserSession;
import com.inoco.kata.shared.DateUtils;
import com.inoco.kata.shared.UserUtils;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/transactions")
@RestController
public class TransactionController {
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
	public List<Transaction> getTransactionHistory() {
		final User currentUser = this.getUserSession();
		System.out.println("User for history " + currentUser);
		return this.transactionRepository.findAll().parallelStream()
				.filter(transaction -> UserUtils.checkUserId(transaction, currentUser.getId()))
				.collect(Collectors.toList());
	}

	@PostMapping("/accountStatement/{startDate}-{endDate}")
	public List<Transaction> getAccountStatement(@PathVariable final Date startDate, @PathVariable final Date endDate) {
		return this.transactionRepository.findAll().parallelStream()
				.filter(transaction -> UserUtils.checkUserId(transaction, this.getUserSession().getId())
						&& DateUtils.compareDate(transaction.getDate(), startDate, endDate))
				.collect(Collectors.toList());
	}

	@PutMapping("/deposit")
	public Transaction getAccountStatement(@RequestBody final Transaction transaction) {
		final User currentUser = this.getUserSession();

		currentUser.setBalance(currentUser.getBalance() + transaction.getAmount());
		transaction.setIdUser(this.getUserSession().getId());

		this.userRepository.save(currentUser);

		System.out.println("After save deposit. User = " + this.getUserSession());

		return this.transactionRepository.save(transaction);
	}

	@PutMapping("/withdrawal")
	public Transaction toMakeWithdrawal(@RequestBody final Transaction transaction) {
		transaction.setIdUser(this.getUserSession().getId());

		return this.transactionRepository.save(transaction);
	}

	private User getUserSession() {
		return this.userSession.getCurrentUser();
	}

	private void setUserSession(final User user) {
		this.userSession.setCurrentUser(user);
	}
}
