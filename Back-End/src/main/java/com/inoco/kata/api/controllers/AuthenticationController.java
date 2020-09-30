package com.inoco.kata.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.inoco.kata.api.model.dto.AuthenticationDto;
import com.inoco.kata.api.model.dto.UserDto;
import com.inoco.kata.api.service.AuthenticationService;
import com.inoco.kata.api.shared.execption.UnauthorizedUserException;

@RestController
public class AuthenticationController {
	private final AuthenticationService authenticationService;

	public AuthenticationController(final AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/login")
	public UserDto login(@RequestBody final AuthenticationDto userAuth) throws UnauthorizedUserException {
		return this.authenticationService.login(userAuth);
	}

	@GetMapping("/logout")
	public boolean logout() {
		return this.authenticationService.logout();
	}
}
