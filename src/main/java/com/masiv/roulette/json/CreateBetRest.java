package com.masiv.roulette.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.masiv.roulette.dto.RouletteDTO;

import lombok.Data;
/**
 * 
 * @author srcortes
 *
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class CreateBetRest {
	@JsonProperty("APUESTA COLOR O NUMERO")
	private String bet;
	@JsonProperty("CANTIDAD APOSTAR")
	private String amount;
}
