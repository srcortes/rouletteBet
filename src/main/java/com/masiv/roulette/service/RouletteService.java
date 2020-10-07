package com.masiv.roulette.service;

import com.masiv.roulette.exceptions.ManagerApiException;
import com.masiv.roulette.json.CreateRouletteRest;
/**
 * This interface represent the methods that is use in the class that implements
 * this interface aditional this methods are requested in the test 
 * @author srcortes
 */
public interface RouletteService {
	CreateRouletteRest createRoulette() throws ManagerApiException;
	void createStateRoulette() throws ManagerApiException;
}
