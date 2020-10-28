package com.masiv.roulette.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.masiv.roulette.dto.RouletteDTO;
import com.masiv.roulette.dto.StateDTO;
import com.masiv.roulette.exceptions.ManagerApiException;
import com.masiv.roulette.json.CreateRouletteRest;
import com.masiv.roulette.repositories.RouletteRepository;
import com.masiv.roulette.service.implement.RouletteServiceImplement;
import com.masiv.roulette.util.IntegrationUtil;

/**
 * 
 * @author srcortes
 *
 */
public class RouletteServiceTest {
	private static final RouletteDTO ROULETTE_DTO = new RouletteDTO();	
	private static final CreateRouletteRest CREATE_ROULETTE_TEST = new CreateRouletteRest();
	private static final long ID_ROULETTE = IntegrationUtil.generateKey();
	@Mock
	private RouletteRepository rouletteRepository;
	@Mock
	private ModelMapper modelMapper;
	@InjectMocks
	private RouletteServiceImplement rouletteServiceImplement;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		ROULETTE_DTO.setIdRoulette(ID_ROULETTE);
		StateDTO STATE_DTO = new StateDTO(1, "Create");
		ROULETTE_DTO.setIdState(STATE_DTO);		
		CREATE_ROULETTE_TEST.setIdRoulette(ROULETTE_DTO.getIdRoulette());
		CREATE_ROULETTE_TEST.setIdState(STATE_DTO);
		
	}
	@Test
	public void createRouletteTest() throws ManagerApiException{
		Mockito.doNothing().when(rouletteRepository).createRoulette(ROULETTE_DTO);
		Mockito.when(modelMapper.map(ROULETTE_DTO, CreateRouletteRest.class)).thenReturn(CREATE_ROULETTE_TEST);
		rouletteServiceImplement.createRoulette();
		assertNotNull(rouletteServiceImplement.createRoulette());			
	}
	@Test(expected = ManagerApiException.class)
	public void createRouletteErrorTest() throws ManagerApiException {
		Mockito.doThrow(Exception.class).when(rouletteRepository).createRoulette(Mockito.any(RouletteDTO.class));	
		rouletteServiceImplement.createRoulette();
		fail();		
	}
	@Test
	public void changeStateRouletteTest() {
		
	}
	
}