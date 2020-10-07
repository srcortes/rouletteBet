package com.masiv.roulette.exceptions;

import java.util.ArrayList;
import java.util.List;
/**
 * This class handle exception
 * @author srcortes
 */
public class ManagerApiException extends Exception {
	private static final long serialVersionUID = 1L;	
	private final String code;	
	private final int responseCode;	
	private final List<ApiError> errorList = new ArrayList<>();	
	public ManagerApiException(String code, int responseCode, String message) {
		super(message);
		this.code = code;
		this.responseCode = responseCode;
	}
	public ManagerApiException(String code, int responseCode, String message, List<ApiError> errorList) {
		super(message);
		this.code = code;
		this.responseCode = responseCode;		
	}
}
