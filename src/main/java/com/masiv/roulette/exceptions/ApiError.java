package com.masiv.roulette.exceptions;

import java.io.Serializable;

import lombok.Data;
/**
 * this class is a data acces object for ManagerApiException
 * @author srcortes
 */
@Data
public class ApiError implements Serializable {	
	private static final long serialVersionUID = 1L;
	private String name;
	private String value;
}
