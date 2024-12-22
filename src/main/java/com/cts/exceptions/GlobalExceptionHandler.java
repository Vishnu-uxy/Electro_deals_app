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
}
