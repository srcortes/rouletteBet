package com.masiv.roulette.service;

import java.util.List;

import com.masiv.roulette.exceptions.InternalServerErrorException;
import com.masiv.roulette.exceptions.ManagerApiException;
import com.masiv.roulette.exceptions.NotFoundException;
import com.masiv.roulette.json.CreateRouletteRest;
import com.masiv.roulette.json.ListRouletteRest;
/**
 * This interface represent the methods that is use in the class that implements
 * this interface aditional this methods are requested in the test 
 * @author srcortes
 */
public interface RouletteService {
	CreateRouletteRest createRoulette() throws InternalServerErrorException;
	void createStateRoulette() throws InternalServerErrorException;
	String openingRoulette(Long idRoulette)throws NotFoundException, InternalServerErrorException;
	List<ListRouletteRest> listRoulette() throws InternalServerErrorException;
}
