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
public final class CreateRouletteRest {
	@JsonProperty("idRuleta")
	private long idRoulette;
	@JsonProperty("estado")
	private StateDTO idState;
}
