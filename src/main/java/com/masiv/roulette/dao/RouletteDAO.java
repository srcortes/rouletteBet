package com.masiv.roulette.dao;

import com.masiv.roulette.dto.RouletteDTO;
import com.masiv.roulette.dto.StateDTO;
import com.masiv.roulette.exceptions.ManagerApiException;
/**
 * 
 * @author srcortes
 *
 */
public interface RouletteDAO {
	RouletteDTO createRoulette(RouletteDTO roulette) throws ManagerApiException;
	void createStateRoulette(StateDTO state) throws ManagerApiException;
}
