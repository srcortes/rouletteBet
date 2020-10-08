package com.masiv.roulette.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;
/**
 * This class handle exception
 * @author srcortes
 */
@Data
public class ManagerApiException extends Exception {
	private HttpStatus status;	
	private static final long serialVersionUID = 1L;
	public ManagerApiException(HttpStatus status, String message, Throwable cause) {
		super(message, cause);
		this.status = status;
	}
	public ManagerApiException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}	
}
