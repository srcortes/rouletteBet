package com.masiv.roulette.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;
/**
 * 
 * @author USUARIO
 *
 */
@Data
public class AmountNotPermittedException extends Exception{
	private HttpStatus status;	
	private static final long serialVersionUID = 1L;
	public AmountNotPermittedException(HttpStatus status, String message, Throwable cause) {
		super(message, cause);
		this.status = status;
	}
	public AmountNotPermittedException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}
}
