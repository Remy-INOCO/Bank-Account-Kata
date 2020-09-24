package com.inoco.kata.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inoco.kata.api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
