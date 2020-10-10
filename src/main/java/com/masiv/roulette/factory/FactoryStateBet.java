package com.masiv.roulette.factory;

import com.masiv.roulette.constant.ConstantStateBet;
import com.masiv.roulette.dto.StateBetDTO;

/**
 * 
 * @author srcortes
 *
 */
public final class FactoryStateBet {
	public static StateBetDTO createFirtState() {
		return new StateBetDTO(Integer.valueOf(ConstantStateBet.OPEN.getId()), ConstantStateBet.OPEN.getName());
	}
	public static StateBetDTO createSecondState() {
		return new StateBetDTO(Integer.valueOf(ConstantStateBet.CLOSED.getId()), ConstantStateBet.CLOSED.getName());
	}
}
