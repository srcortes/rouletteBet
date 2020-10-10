package com.masiv.roulette.dto;

import lombok.Data;
/**
 * 
 * @author srcortes
 *
 */
@Data
public final class CreateBetDTO {
	private long idBet;
	private RouletteDTO roulette;
	private Long idUser;
	private String bet;
	private double amount;	
}
