package com.inoco.kata.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inoco.kata.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/transaction")
@RestController
public class TransactionController {
	private final UserRepository repository;

	TransactionController(final UserRepository repository) {
		this.repository = repository;
	}

//	@GetMapping("/add")
//	public Transaction add() {
//		return this.repository.findAll();
//	}
}
