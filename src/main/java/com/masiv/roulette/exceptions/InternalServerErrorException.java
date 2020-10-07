package com.masiv.roulette.exceptions;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
/**
 * This class is a subclass of ManagerApiException, for controls errors server
 * @author srcortes
 *
 */
public class InternalServerErrorException extends ManagerApiException{
	private static final long serialVersionUID = 1L;
	public InternalServerErrorException(String code, String message) {
		super(code,HttpStatus.INTERNAL_SERVER_ERROR.value(),message);
	}	
	public InternalServerErrorException(String code, String message, ApiError data) {
		super(code,HttpStatus.INTERNAL_SERVER_ERROR.value(),message, Arrays.asList(data));
	}


}
