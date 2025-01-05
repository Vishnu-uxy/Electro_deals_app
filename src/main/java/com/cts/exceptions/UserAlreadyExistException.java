package com.cts.exceptions;

public class UserAlreadyExistException extends RuntimeException {
	public UserAlreadyExistException(String message) {
		super(message);
	}

	public UserAlreadyExistException() {
		super("Given email is already exist");
	}
}
