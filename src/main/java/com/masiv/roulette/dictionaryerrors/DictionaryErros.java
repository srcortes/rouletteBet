package com.masiv.roulette.dictionaryerrors;
/**
 * This interface represent the error messages in the api
 * @author srcortes
 */
public enum DictionaryErros {
	ERROR_INTERNAL_SERVER("INTERNAL SERVER ERROR CHECK LOG PLEASE."),
	ERROR_NOT_FOUND("Ruleta no encontrada"),
	ERROR_NUMBER_OUT_RANGE("El número debe estar en el rango de 0 a 36"),
	ERROR_COLORS("Para apuestas por color, solo se permite Rojo o Negro"),
	ERROR_EXCEEDS_LIMIT_BET("La cantidad maxima para apostar son $10.000USD"),
	ROULETTE_NOT_OPENING("No existen ruletas creadas o abiertas, para iniciar apuestas"),
	NOT_ROULETTE_OPENING("El id de ruleta que ingreso no se encuentra en estado Abierta"),
	NOT_EXISTS_BET_FOR_ROULETTE("No existen por el momento apuestas para la ruleta seleccionada"),
	ROULETTE_ISCLOSED("La ruleta que intenta abrir ya se encuentra cerrada");
	private String descriptionErrors;
	private DictionaryErros(String descriptionErrors) {
		this.descriptionErrors = descriptionErrors;
	}
	public String getDescriptionError() {
		return descriptionErrors;
	}
}
