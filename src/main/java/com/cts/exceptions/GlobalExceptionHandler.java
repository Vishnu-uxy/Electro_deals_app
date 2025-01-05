package com.cts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
		return new ResponseEntity<>("EndPoint not found", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserNameNotFoundException.class)
	public ResponseEntity<String> handleUserNameNotFoundException(UserNameNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
