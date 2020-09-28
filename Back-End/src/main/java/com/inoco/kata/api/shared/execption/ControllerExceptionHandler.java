package com.inoco.kata.api.shared.execption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.inoco.kata.api.model.ErrorMessage;

@RestControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(UnauthorizedUserException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public ErrorMessage unauthorizedUser(final UnauthorizedUserException ex, final WebRequest request) {
		return new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
	}

	@ExceptionHandler(CompareDateException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessage compareDate(final CompareDateException ex, final WebRequest request) {
		return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	}

	@ExceptionHandler(UnauthorizedActionException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessage unauthorizedAction(final UnauthorizedActionException ex, final WebRequest request) {
		return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	}
}