package com.masiv.roulette.exceptions;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
/**
 * This class is a subclass of ManagerApiException, for controls errors server
 * @author srcortes
 *
 */
public class InternalServerErrorException extends Exception{
	private HttpStatus status;
	private static final long serialVersionUID = 1L;
	public InternalServerErrorException(HttpStatus status, String message, Throwable cause) {
		super(message, cause);
		this.status = status;
	}
	public InternalServerErrorException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}
}
