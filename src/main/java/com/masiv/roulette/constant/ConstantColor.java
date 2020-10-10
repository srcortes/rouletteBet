package com.masiv.roulette.constant;
/**
 * srcortes
 */
public enum ConstantColor {
	ROJO("rojo"),NEGRO("negro");
	private String colors;
	private ConstantColor(String colors) {
		this.colors = colors;
	}
	public static String byValue(String value){
		ConstantColor[] values = ConstantColor.values();
		for(int i = 0; i < values.length; i++) {
			if(values[i].colors.equalsIgnoreCase(value))
				return "isCorrect";
		}
		return "isNotCorrect";				
	}
	public String getColors() {
		return colors;
	}
}
