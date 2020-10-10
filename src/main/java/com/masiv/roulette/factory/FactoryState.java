package com.masiv.roulette.factory;
import com.masiv.roulette.constant.ConstantState;
import com.masiv.roulette.dto.StateDTO;

import lombok.Data;
/**
 * This class is a factory of object for states of a roulette
 * @author srcortes
 *
 */
public class FactoryState {
	public static StateDTO createFirtState(){
		return new StateDTO(Integer.valueOf(ConstantState.CREATED.getId()), ConstantState.CREATED.getName());
	}
	public static StateDTO createSecondState() {
		return new StateDTO(Integer.valueOf(ConstantState.OPENING.getId()), ConstantState.OPENING.getName());
	}
	public static StateDTO createThirdState() {
		return new StateDTO(Integer.valueOf(ConstantState.CLOSED.getId()), ConstantState.CLOSED.getName());
	}
	public static StateDTO createFourthState(){
		return new StateDTO(Integer.valueOf(ConstantState.IN_USE.getId()), ConstantState.IN_USE.getName());
	}
}
