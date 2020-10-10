package com.masiv.roulette.constant;

public enum ConstantStateBet {
	OPEN("1","Open"), CLOSED("2","Closed");	
	private String id;
	private String name;	
	private ConstantStateBet(String id, String name) {
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
