package com.masiv.roulette.constant;
/**
 * 
 * @author srcortes
 *
 */
public enum ConstantOpeningRoulette {
	SUCCES("Exitosa"), DENIED("Denegada");
	private String message;
	private ConstantOpeningRoulette(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
