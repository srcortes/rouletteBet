package com.masiv.roulette.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.masiv.roulette.dto.RouletteDTO;
import com.masiv.roulette.dto.StateDTO;
import com.masiv.roulette.exceptions.InternalServerErrorException;
import com.masiv.roulette.exceptions.ManagerApiException;
/**
 * 
 * @author srcortes
 *
 */
public interface RouletteDAO {
	RouletteDTO createRoulette(RouletteDTO roulette) throws InternalServerErrorException;
	void createStateRoulette(StateDTO state) throws InternalServerErrorException;
	void openingRoulette(Long idRoulette)throws InternalServerErrorException;
	List<RouletteDTO> listRoulette()throws InternalServerErrorException;
}
