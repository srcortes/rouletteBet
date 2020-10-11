package com.masiv.roulette.dto;

import java.io.Serializable;

import lombok.Data;
/**
 * This class is a dto for Roulette
 * @author srcortes
 *
 */
@Data
public final class RouletteDTO implements Serializable{
	private long idRoulette;
	private StateDTO idState; 
}
