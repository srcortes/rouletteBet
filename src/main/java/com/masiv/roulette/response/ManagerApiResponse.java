package com.masiv.roulette.response;



import java.io.Serializable;

import lombok.Data;
/**
 * This class represent the response when try consume the service
 * @author srcortes
 *
 * @param <T> this param represent the object that is receive from front end
 */
@Data
public class ManagerApiResponse<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private String status;
	private String code;
	private String message;
	private T dataInformation;
	/**
	 * Overloaded Constructors
	 * @param status
	 * @param code
	 * @param message
	 */
	public ManagerApiResponse(String status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
	/**
	 * Overloaded Constructors
	 * @param status
	 * @param code
	 * @param message
	 * @param dataInformation
	 */
	public ManagerApiResponse(String status, String code, String message, T dataInformation) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.dataInformation = dataInformation;
	}
}
