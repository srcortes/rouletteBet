package com.masiv.roulette.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.masiv.roulette.dto.RouletteDTO;
import com.masiv.roulette.dto.StateDTO;
import com.masiv.roulette.exceptions.ManagerApiException;
import com.masiv.roulette.exceptions.InternalServerErrorException;
import com.masiv.roulette.json.CreateRouletteRest;
import com.masiv.roulette.repositories.RouletteRepository;
import com.masiv.roulette.service.implement.RouletteServiceImplement;

public class RouletteServiceTest {
	private final RouletteDTO ROULETTE_DTO = new RouletteDTO();
	private final RouletteDTO ROULETTE_DTO_RESPONSE = new RouletteDTO();
	private final CreateRouletteRest  CREATE_ROULETTE_REST = new CreateRouletteRest();
	private StateDTO STATE_ROULETTE_DTO = null;
	private final String ID_STATE = "1";
	private final String DESCRIPTION_STATE = "Created";
	@Mock
	RouletteRepository rouletteRepository;
	@InjectMocks
	RouletteServiceImplement rouletteServiceImplement;
	@Before
	public void init() throws ManagerApiException {
		MockitoAnnotations.initMocks(this);
		Integer primaryKey = Integer.valueOf(String.valueOf(System.currentTimeMillis()).substring(4));
		STATE_ROULETTE_DTO = new StateDTO(Integer.valueOf(ID_STATE),DESCRIPTION_STATE);
		ROULETTE_DTO.setIdRoulette(primaryKey);
		ROULETTE_DTO.setIdState(STATE_ROULETTE_DTO);
		ROULETTE_DTO_RESPONSE.setIdRoulette(primaryKey);
		ROULETTE_DTO_RESPONSE.setIdState(STATE_ROULETTE_DTO);
		CREATE_ROULETTE_REST.setIdRoulette(ROULETTE_DTO.getIdRoulette());
		CREATE_ROULETTE_REST.setIdState(STATE_ROULETTE_DTO);		
	}
	@Test
	public void createStateRouletteTest() throws ManagerApiException {
		Mockito.doNothing().when(rouletteRepository).createStateRoulette(STATE_ROULETTE_DTO);
		rouletteServiceImplement.createStateRoulette();		
	}
	@Test(expected = InternalServerErrorException.class)
	public void createStateRouletteErrorTest() throws ManagerApiException {
		Mockito.doThrow(InternalServerErrorException.class).when(rouletteRepository).createStateRoulette(STATE_ROULETTE_DTO);
		rouletteServiceImplement.createStateRoulette();
		fail();
		
	}
	@Test
	public void createRouletteTest() throws ManagerApiException {
		Mockito.when(rouletteRepository.createRoulette(ROULETTE_DTO)).thenReturn(ROULETTE_DTO_RESPONSE);
		assertEquals(CREATE_ROULETTE_REST, rouletteServiceImplement.createRoulette());
	}
	@Test(expected = InternalServerErrorException.class)
	public void createRouletteErrorTest() throws ManagerApiException{
		Mockito.when(rouletteRepository.createRoulette(ROULETTE_DTO)).thenReturn(ROULETTE_DTO_RESPONSE);			
		rouletteServiceImplement.createRoulette();
		fail();
	}

}
