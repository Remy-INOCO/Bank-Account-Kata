package com.inoco.kata.api.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inoco.kata.api.model.Transaction;
import com.inoco.kata.api.model.dto.TransactionDto;
import com.inoco.kata.api.service.TransactionService;
import com.inoco.kata.api.shared.execption.CompareDateException;
import com.inoco.kata.api.shared.execption.UnauthorizedActionException;
import com.inoco.kata.api.shared.execption.UnauthorizedUserException;

@RequestMapping("/transactions")
@RestController
public class TransactionController {
	private final TransactionService transactionService;

	public TransactionController(final TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@GetMapping("/history")
	public List<TransactionDto> getTransactionsHistory() throws UnauthorizedUserException {
		return this.transactionService.getTransactionsHistory();
	}

	@GetMapping("/accountStatement/{startDate}-{endDate}")
	public List<TransactionDto> getAccountStatements(@PathVariable final Date startDate,
			@PathVariable final Date endDate) throws UnauthorizedUserException, CompareDateException {
		return this.transactionService.getAccountStatements(startDate, endDate);
	}

	@PutMapping("/deposit")
	public Transaction toMakeDeposit(@RequestBody final TransactionDto transaction) throws UnauthorizedUserException {
		return this.transactionService.toMakeDeposit(transaction);
	}

	@PutMapping("/withdrawal")
	public Transaction toMakeWithdrawal(@RequestBody final TransactionDto transaction)
			throws UnauthorizedUserException, UnauthorizedActionException {

		return this.transactionService.toMakeWithdrawal(transaction);
	}
}
