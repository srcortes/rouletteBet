package com.masiv.roulette.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.masiv.roulette.dto.StateDTO;
import com.masiv.roulette.exceptions.ManagerApiException;
import com.masiv.roulette.json.CreateRouletteRest;
import com.masiv.roulette.response.ManagerApiResponse;
import com.masiv.roulette.service.RouletteService;


public class RouletteControllerTest {
	private final CreateRouletteRest  CREATE_ROULETTE_REST = new CreateRouletteRest();	
	private final String ID_STATE = "1";
	private final String DESCRIPTION_STATE = "Created";
	private StateDTO STATE_ROULETTE_DTO = null;
	private static final String SUCCES_STATUS = "Succes";
	private static final String SUCCES_CODE = "200 OK";
	private static final String SUCCES_MESSAGE = "OK";
	@Mock
	RouletteService rouletteService;
	@InjectMocks
	RouletteController rouletteController;	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);		
		STATE_ROULETTE_DTO = new StateDTO(Integer.valueOf(ID_STATE),DESCRIPTION_STATE);		
		CREATE_ROULETTE_REST.setIdState(STATE_ROULETTE_DTO);
		CREATE_ROULETTE_REST.setIdRoulette(Integer.valueOf(String.valueOf(System.currentTimeMillis()).substring(4)));
	}	
	@Test
	public void createRouletteTest() throws ManagerApiException {
		Mockito.when(rouletteService.createRoulette()).thenReturn(CREATE_ROULETTE_REST);
		ManagerApiResponse<CreateRouletteRest> response = rouletteController.createRoulette();
		assertEquals(response.getCode(), SUCCES_CODE);
		assertEquals(response.getMessage(), SUCCES_MESSAGE);
		assertEquals(response.getStatus(), SUCCES_STATUS);
		assertEquals(response.getDataInformation(), CREATE_ROULETTE_REST);	
	}
	@Test(expected = ManagerApiException.class)
	public void createRouletteErrorTest() throws ManagerApiException {
		Mockito.doThrow(ManagerApiException.class).when(rouletteService).createRoulette();
		ManagerApiResponse<CreateRouletteRest> response = rouletteController.createRoulette();
		assertEquals(ManagerApiException.class, ManagerApiException.class);
		fail();
	}
	

}
