package com.masiv.roulette.dto;

import lombok.Data;
/**
 * 
 * @author srcortes
 *
 */
@Data
public final class StateDTO {
	private int idState;
	private String description;
	public StateDTO(int idState, String description) {
		this.idState = idState;
		this.description = description;
	}
}
