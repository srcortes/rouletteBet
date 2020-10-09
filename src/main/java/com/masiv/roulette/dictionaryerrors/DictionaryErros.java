package com.masiv.roulette.dictionaryerrors;
/**
 * This interface represent the error messages in the api
 * @author srcortes
 */
public enum DictionaryErros {
	ERROR_INTERNAL_SERVER("INTERNAL SERVER ERROR CHECK LOG PLEASE."),
	ERROR_NOT_FOUND("ROULETTE_NOT_FOUND"),
	ERROR_NUMBER_OUT_RANGE("El número debe estar en el rango de 0 a 36"),
	ERROR_COLORS("Para apuestas por color, solo se permite Rojo o Negro"),
	ERROR_EXCEEDS_LIMIT_BET("La cantidad maxima para apostar son $10.000USD");
	private String descriptionErrors;
	private DictionaryErros(String descriptionErrors) {
		this.descriptionErrors = descriptionErrors;
	}
	public String getDescriptionError() {
		return descriptionErrors;
	}
}
