package com.masiv.roulette.constant;
/**
 * srcortes
 */
public enum ConstantLimitAmount {
    LIMIT_AMOUNT("10000");
	private String amount;
	private ConstantLimitAmount(String amount) {
		this.amount = amount;
	}
	public String getAmount() {
		return amount;
	}
}
