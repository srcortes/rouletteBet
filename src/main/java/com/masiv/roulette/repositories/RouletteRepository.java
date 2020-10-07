package com.masiv.roulette.repositories;


import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.masiv.roulette.dao.RouletteDAO;
import com.masiv.roulette.dto.RouletteDTO;
import com.masiv.roulette.dto.StateDTO;
import com.masiv.roulette.exceptions.ManagerApiException;
import com.masiv.roulette.util.IntegrationUtil;
/**
* this interface represent the comunication with database
* @author srcortes
*
*/
@Repository
public class RouletteRepository implements RouletteDAO {	
	NamedParameterJdbcTemplate template;	
	public RouletteRepository (NamedParameterJdbcTemplate template) {
		this.template = template;
	}
	@Override
	public RouletteDTO createRoulette(RouletteDTO roulette) throws ManagerApiException {
		KeyHolder holder = new GeneratedKeyHolder();		
		RouletteDTO rouletteDTO = roulette;		
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("ID_ROULETTE",rouletteDTO.getIdRoulette())
				.addValue("ID_STATE", rouletteDTO.getIdState().getIdState());
		template.update("INSERT INTO MANAGER.ROULETTE (ID_ROULETTE, ID_STATE) VALUES (:ID_ROULETTE, :ID_STATE)", param, holder);
		return rouletteDTO;
	}
	@Override
	public void createStateRoulette(StateDTO state) throws ManagerApiException {
		KeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("ID_STATE", state.getIdState())
				.addValue("DESCRIPTION", state.getDescription());
		template.update("INSERT INTO  MANAGER.STATE (ID_STATE, DESCRIPTION) VALUES (:ID_STATE, :DESCRIPTION)", param, holder);		
	}
}
