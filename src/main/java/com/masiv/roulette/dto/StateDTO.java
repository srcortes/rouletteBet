package com.masiv.roulette.dto;

import java.io.Serializable;

import lombok.Data;
/**
 * 
 * @author srcortes
 *
 */
@Data
public final class StateDTO implements Serializable {
	private int idState;
	private String description;
	public StateDTO(int idState, String description) {
		this.idState = idState;
		this.description = description;
	}
}
