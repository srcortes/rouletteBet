package com.masiv.roulette.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.masiv.roulette.dto.StateDTO;

import lombok.Data;

/**
 * This class is a json representation for the view
 * @author srcortes
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListRouletteRest {
	@JsonProperty("Id_Asignado")
	private long idRoulette;
	@JsonProperty("Estado_Ruleta")
	private StateDTO idState;
}
