package com.masiv.roulette.dao;

import java.util.List;

import com.masiv.roulette.dto.CreateBetDTO;
import com.masiv.roulette.dto.RouletteDTO;
import com.masiv.roulette.dto.StateDTO;
import com.masiv.roulette.exceptions.ManagerApiException;
import com.masiv.roulette.json.BetUserRest;
/**
 * 
 * @author srcortes
 *
 */
public interface RouletteDAO {
	RouletteDTO createRoulette(RouletteDTO roulette) throws ManagerApiException;
	void createStateRoulette(StateDTO state) throws ManagerApiException;
	void openingRoulette(Long idRoulette)throws ManagerApiException;
	List<RouletteDTO> listRoulette() throws ManagerApiException;
	CreateBetDTO generateBet(CreateBetDTO createBetDTO) throws ManagerApiException;
}
