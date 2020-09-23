package com.inoco.kata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inoco.kata.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
