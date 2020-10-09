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
public class BetUserRest {
	@JsonProperty("Id_Asignado")
	private Long idBet;
	@JsonProperty("Ruleta_Asignada")
	private RouletteDTO roulette;
	@JsonProperty("Identificacion_Usuario")
	private Long idUser;
	@JsonProperty("Tipo_Apuesta")
	private String bet;
	@JsonProperty("Cantidad_Apostada")
	private double amount;	
}
