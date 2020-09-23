package com.inoco.kata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inoco.kata.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
