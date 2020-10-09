package com.masiv.roulette.constant;

import lombok.Data;
/**
 * Enum generals for the app
 * @author srcortes
 */
public enum ConstantState {
	CREATED("1","Created"), OPENING("2","Opening"), CLOSED("3","Closed"), IN_USE("4","Assignement");	
	private String id;
	private String name;	
	private ConstantState(String id, String name) {
		this.name = name;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
}
