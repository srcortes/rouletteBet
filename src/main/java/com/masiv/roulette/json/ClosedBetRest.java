package com.masiv.roulette.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.masiv.roulette.dto.CreateBetDTO;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ClosedBetRest {
	@JsonProperty("Id_Emision_Cierre")
	private int idResult;
	@JsonProperty("Mesa ganadores")
	private CreateBetDTO createBetDTO;
	@JsonProperty("Ganador")
	private Long idUser;
	@JsonProperty("Valor_Ganado")
	private double earnedValue;	
	@JsonProperty("Numero_Generado_Sistema")
	private int numberGenerate;
	@JsonProperty("Tipo apuesta color o numero")
	private String typeBet;
	@JsonProperty("Fecha_Emision_Cierre")
	private String dateEmission; 
}
