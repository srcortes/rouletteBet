package com.masiv.roulette.service;

import java.util.List;

import com.masiv.roulette.dto.CreateBetDTO;
import com.masiv.roulette.dto.RouletteDTO;
import com.masiv.roulette.exceptions.ManagerApiException;
import com.masiv.roulette.json.BetUserRest;
import com.masiv.roulette.json.CreateBetRest;
import com.masiv.roulette.json.CreateRouletteRest;
import com.masiv.roulette.json.ListRouletteRest;
/**
 * This interface represent the methods that is use in the class that implements
 * this interface aditional this methods are requested in the test 
 * @author srcortes
 */
public interface RouletteService {
	CreateRouletteRest createRoulette() throws ManagerApiException;	
	String openingRoulette(Long idRoulette)throws Exception;
	List<ListRouletteRest> listRoulette() throws ManagerApiException;
	BetUserRest listBetUser(CreateBetRest createBetRest, Long idUsuario) throws Exception;
	void createStateBet() throws ManagerApiException;
	void createStateRoulette() throws ManagerApiException;	
	default RouletteDTO selectionRouletteOpening()throws ManagerApiException{
		
		return null;		
	}
	default BetUserRest createBet(CreateBetRest createBetRest,Long idUser)throws ManagerApiException{
		
		return null;
	}
}
