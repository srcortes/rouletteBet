package com.masiv.roulette.exceptions;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
/**
 * This class is responsible handle exception when value is not found in the database
 * @author srcortes
 *
 */
public class NotFoundException extends ManagerApiException {	
	private static final long serialVersionUID = 1L;
	/**
	 * Overloaded Constructor
	 * @param code
	 * @param message
	 */
	public NotFoundException(String code, String message) {
		super(code,HttpStatus.NOT_FOUND.value(),message);
	}
	/**
	 * Overloaded Constructor
	 * @param code
	 * @param message
	 * @param data
	 */
	public NotFoundException(String code, String message, ApiError data) {
		super(code,HttpStatus.NOT_FOUND.value(),message, Arrays.asList(data));
	}
}
