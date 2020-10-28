package com.masiv.roulette.dao;

import java.util.List;

import com.masiv.roulette.dto.ClosedBetDTO;
import com.masiv.roulette.dto.CreateBetDTO;
import com.masiv.roulette.dto.RouletteDTO;
import com.masiv.roulette.dto.StateBetDTO;
import com.masiv.roulette.dto.StateDTO;
import com.masiv.roulette.exceptions.ManagerApiException;
import com.masiv.roulette.json.BetUserRest;
/**
 * 
 * @author srcortes
 *
 */
public interface RouletteDAO {
	void createRoulette(RouletteDTO roulette) throws ManagerApiException;
	void createStateRoulette(StateDTO state) throws ManagerApiException;
	void createStateBet(StateBetDTO stateBetDTO) throws ManagerApiException;
	void changeStateRoulette(Long idRoulette, Integer idState)throws ManagerApiException;
	void closedBet(Long idRoulette)throws ManagerApiException;
	void createFinalScore(ClosedBetDTO closedBetDTO)throws ManagerApiException;	
	List<RouletteDTO> listRoulette() throws ManagerApiException;
	List<CreateBetDTO> existsBetByRoulette(Long idRoulette) throws ManagerApiException;
	List<ClosedBetDTO> selectPersonWinner(Long idRoulette, int filterNumberGenerate) throws ManagerApiException;
	CreateBetDTO generateBet(CreateBetDTO createBetDTO) throws ManagerApiException;	
}
