package com.cts.exceptions;

public class UserNameNotFoundException extends RuntimeException {
	public UserNameNotFoundException(String message) {
		super(message);
	}

	public UserNameNotFoundException() {
		super("user not found");
	}

}
