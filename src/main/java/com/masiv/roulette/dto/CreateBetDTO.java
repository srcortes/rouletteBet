package com.masiv.roulette.dto;

import java.io.Serializable;

import lombok.Data;
/**
 * 
 * @author srcortes
 *
 */
@Data
public final class CreateBetDTO implements Serializable{
	private long idBet;
	private RouletteDTO roulette;
	private Long idUser;
	private String bet;
	private double amount;	
}
