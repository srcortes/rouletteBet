package com.masiv.roulette.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.masiv.roulette.dto.StateDTO;
import com.masiv.roulette.exceptions.ManagerApiException;
import com.masiv.roulette.json.BetUserRest;
import com.masiv.roulette.json.CreateBetRest;
import com.masiv.roulette.json.CreateRouletteRest;
import com.masiv.roulette.json.ListRouletteRest;
import com.masiv.roulette.response.ManagerApiResponse;
import com.masiv.roulette.service.RouletteService;
import com.masiv.roulette.util.IntegrationUtil;
/**
 * 
 * @author srcortes
 *
 */
public class RouletteControllerTest {
	private static final CreateRouletteRest CREATE_ROULETTE_REST = new CreateRouletteRest();	
	private static final long ID_ROULETTE = IntegrationUtil.generateKey();
	private static final String SUCCES_STATUS = "Succes";
	private static final String SUCCES_CODE = "200 OK";
	private static final String SUCCES_MESSAGE = "OK";
	private static final String EXITOSA = "Exitosa";
	
	@Mock
	private RouletteService rouletteService;
	@InjectMocks
	private RouletteController rouletteController;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		//Create Object of type CreateRouletteRest
		CREATE_ROULETTE_REST.setIdRoulette(ID_ROULETTE);
		StateDTO STATE_DTO = new StateDTO(1, "Create");
		CREATE_ROULETTE_REST.setIdState(STATE_DTO);
	}
	
	@Test
	public void createRouletteTest() throws ManagerApiException {
		Mockito.when(rouletteService.createRoulette()).thenReturn(CREATE_ROULETTE_REST);
		ManagerApiResponse<CreateRouletteRest> response = rouletteController.createRoulette();
		assertEquals(SUCCES_STATUS, response.getStatus());
		assertEquals(SUCCES_CODE, response.getCode());
		assertEquals(SUCCES_MESSAGE,response.getMessage());
		assertEquals(CREATE_ROULETTE_REST, response.getDataInformation());
		assertNotNull(response.getDataInformation());
	}
	@Test
	public void openingRoulette() throws Exception{
		Mockito.when(rouletteService.changeStateRoulette(ID_ROULETTE)).thenReturn(EXITOSA);
		ManagerApiResponse<String> response = rouletteController.openingRoulette(ID_ROULETTE);
		assertEquals(SUCCES_STATUS, response.getStatus());
		assertEquals(SUCCES_CODE, response.getCode());
		assertEquals(SUCCES_MESSAGE,response.getMessage());
		assertEquals(EXITOSA, response.getDataInformation());
		assertNotNull(response.getDataInformation());
	}
	
	
}
