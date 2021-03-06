package com.masiv.roulette.repositories;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.masiv.roulette.constant.ConstantColor;
import com.masiv.roulette.constant.ConstantState;
import com.masiv.roulette.constant.ConstantStateBet;
import com.masiv.roulette.dao.RouletteDAO;
import com.masiv.roulette.dto.ClosedBetDTO;
import com.masiv.roulette.dto.CreateBetDTO;
import com.masiv.roulette.dto.RouletteDTO;
import com.masiv.roulette.dto.StateBetDTO;
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
	public void createRoulette(RouletteDTO roulette) throws ManagerApiException {
		KeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("ID_ROULETTE",roulette.getIdRoulette())
				.addValue("ID_STATE", roulette.getIdState().getIdState());
		template.update("INSERT INTO MANAGER.ROULETTE (ID_ROULETTE, ID_STATE) VALUES (:ID_ROULETTE, :ID_STATE)", param, holder);		
	}
	@Override
	public void createStateRoulette(StateDTO state) throws ManagerApiException {
		KeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("ID_STATE", state.getIdState())
				.addValue("DESCRIPTION", state.getDescription());
		template.update("INSERT INTO  MANAGER.STATE (ID_STATE, DESCRIPTION) VALUES (:ID_STATE, :DESCRIPTION)", param, holder);		
	}
	@Override
	public void changeStateRoulette(Long idRoulette, Integer idState) throws ManagerApiException {
		final String sqlOpeningRoulette = "UPDATE MANAGER.ROULETTE SET ID_STATE = "+idState+" WHERE ID_ROULETTE=:idRoulette";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idRoulette", idRoulette);
		template.execute(sqlOpeningRoulette, map, new PreparedStatementCallback<Object>() {
			@Override
			public Object doInPreparedStatement(java.sql.PreparedStatement ps)
					throws java.sql.SQLException, DataAccessException {
				return ps.executeUpdate();
			}			
		});		
	}
	@Override
	public List<RouletteDTO> listRoulette() throws ManagerApiException {
		final String sqlListRoulettes = "SELECT ID_ROULETTE, S.ID_STATE, S.DESCRIPTION FROM MANAGER.ROULETTE R "
				+ "JOIN MANAGER.STATE S ON (R.ID_STATE = S.ID_STATE)";
		return template.query(sqlListRoulettes, (rs, rowNumber) -> {
			RouletteDTO rouletteDTO = new RouletteDTO();
			StateDTO stateDTO = new StateDTO(rs.getInt("ID_STATE"), rs.getString("DESCRIPTION"));
			rouletteDTO.setIdRoulette(rs.getLong("ID_ROULETTE"));
			rouletteDTO.setIdState(stateDTO);
			return rouletteDTO;
		});
	}
	@Override
	public CreateBetDTO generateBet(CreateBetDTO createBetDTO) throws ManagerApiException {
		KeyHolder holder = new GeneratedKeyHolder();
		CreateBetDTO createBet = createBetDTO;
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("ID_BET", createBet.getIdBet())
				.addValue("ID_ROULETTE", createBetDTO.getRoulette().getIdRoulette())
				.addValue("ID_GAMBLES", createBet.getIdUser())
				.addValue("ID_STATEBET", ConstantStateBet.OPEN.getId())
				.addValue("BET", createBet.getBet())
				.addValue("AMOUNT", createBet.getAmount());
		template.update("INSERT INTO MANAGER.BET_USER (ID_BET, ID_ROULETTE, ID_GAMBLES, ID_STATEBET, BET, AMOUNT) VALUES (:ID_BET, :ID_ROULETTE, :ID_GAMBLES, :ID_STATEBET, :BET, :AMOUNT)", param, holder);
		return createBet;
	}
	@Override
	public void createStateBet(StateBetDTO stateBetDTO) throws ManagerApiException {
		KeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("ID_STATEBET", stateBetDTO.getIdStateBet())
				.addValue("DESCRIPTION", stateBetDTO.getDescription());
		template.update("INSERT INTO MANAGER.STATE_BET (ID_STATEBET, DESCRIPTION) VALUES (:ID_STATEBET, :DESCRIPTION)", param, holder);
		
	}
	@Override
	public List<CreateBetDTO> existsBetByRoulette(Long idRoulette) throws ManagerApiException {
		final String sqlExistsBets = "SELECT ID_BET FROM MANAGER.BET_USER WHERE ID_ROULETTE = " + idRoulette +"";
		return template.query(sqlExistsBets, (rs, rowNumber) -> {
			CreateBetDTO createBetDTO = new CreateBetDTO();
			createBetDTO.setIdBet(rs.getLong("ID_BET"));
			return createBetDTO;
		});	
	}
	@Override
	public void closedBet(Long idRoulette) throws ManagerApiException {
		final String sqlOpeningRoulette = "UPDATE MANAGER.BET_USER SET ID_STATEBET = "+ConstantStateBet.CLOSED.getId()+" WHERE ID_ROULETTE=:idRoulette";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idRoulette", idRoulette);
		template.execute(sqlOpeningRoulette, map, new PreparedStatementCallback<Object>() {
			@Override
			public Object doInPreparedStatement(java.sql.PreparedStatement ps)
					throws java.sql.SQLException, DataAccessException {
				return ps.executeUpdate();
			}			
		});	
		
	}
	@Override
	public List<ClosedBetDTO> selectPersonWinner(Long idRoulette, int filterNumberGenerate) throws ManagerApiException {		
		String winnerByColor = filterNumberGenerate % 2 == 0 ? ConstantColor.ROJO.getColors(): ConstantColor.NEGRO.getColors();
		final String sqlExistsBets = "SELECT ID_BET, ID_GAMBLES, AMOUNT, BET, ID_ROULETTE FROM MANAGER.BET_USER WHERE ID_ROULETTE = " + idRoulette +" AND (BET ="+filterNumberGenerate+" OR BET = " +"'"+winnerByColor+"'"+ ")";
		return template.query(sqlExistsBets, (rs, rowNumber) -> {
			CreateBetDTO createBetDTO = new CreateBetDTO();
			ClosedBetDTO closedBetDTO = new ClosedBetDTO();
			RouletteDTO rouletteDTO = new RouletteDTO();
			rouletteDTO.setIdRoulette(rs.getLong("ID_ROULETTE"));
			createBetDTO.setRoulette(rouletteDTO);
			createBetDTO.setIdBet(rs.getLong("ID_BET"));
			createBetDTO.setIdUser(rs.getLong("ID_GAMBLES"));			
			createBetDTO.setAmount(rs.getDouble("AMOUNT"));
			createBetDTO.setBet(rs.getString("BET"));			
			closedBetDTO.setCreateBetDTO(createBetDTO);
			closedBetDTO.setNumberGenerate(filterNumberGenerate);
			return closedBetDTO;
			
		});		
	}
	@Override
	public void createFinalScore(ClosedBetDTO closedBetDTO) throws ManagerApiException {
		KeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("ID_RESULT", IntegrationUtil.generateKey() * IntegrationUtil.generateNumberRandom())
				.addValue("ID_BET", closedBetDTO.getCreateBetDTO().getIdBet())
				.addValue("ID_GAMBLES", closedBetDTO.getCreateBetDTO().getIdUser())
				.addValue("BET", closedBetDTO.getCreateBetDTO().getBet())
				.addValue("NUMBER_GENERATE", closedBetDTO.getNumberGenerate())
				.addValue("EARNED_VALUE", closedBetDTO.getEarnedValue())
				.addValue("DATE_EMISSION", closedBetDTO.getDateEmission());
		template.update("INSERT INTO MANAGER.EMISSION_RESULT (ID_RESULT, ID_BET, ID_GAMBLES, BET, NUMBER_GENERATE, EARNED_VALUE, DATE_EMISSION) VALUES (:ID_RESULT, :ID_BET, :ID_GAMBLES, :BET, :NUMBER_GENERATE, :EARNED_VALUE, :DATE_EMISSION)",
				param, holder);

	}
}
