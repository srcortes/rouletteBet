package com.masiv.roulette.exceptions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;
/**
 * this class is a data acces object for ManagerApiException
 * @author srcortes
 */
@Data
public class ApiError implements Serializable {	
	private HttpStatus status;
	private String message;
	private List<String> errors;
	public ApiError(HttpStatus status, String message, List<String> errors) {
		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
	}
	public ApiError(HttpStatus status, String message, String error) {
		super();
		this.status = status;
		this.message = message;
		errors = Arrays.asList(error);
	}
}
