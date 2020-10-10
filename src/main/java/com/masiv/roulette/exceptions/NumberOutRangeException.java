package com.masiv.roulette.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;
@Data
public class NumberOutRangeException extends Exception {
	private HttpStatus status;	
	private static final long serialVersionUID = 1L;
	public NumberOutRangeException(HttpStatus status, String message, Throwable cause) {
		super(message, cause);
		this.status = status;
	}
	public NumberOutRangeException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}
}
