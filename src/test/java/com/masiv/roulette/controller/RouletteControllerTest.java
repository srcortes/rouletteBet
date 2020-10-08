package com.masiv.roulette.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.masiv.roulette.dto.StateDTO;
import com.masiv.roulette.exceptions.InternalServerErrorException;
import com.masiv.roulette.exceptions.ManagerApiException;
import com.masiv.roulette.exceptions.NotFoundException;
import com.masiv.roulette.json.CreateRouletteRest;
import com.masiv.roulette.json.ListRouletteRest;
import com.masiv.roulette.response.ManagerApiResponse;
import com.masiv.roulette.service.RouletteService;
/**
 * 
 * @author srcortes
 *
 */
public class RouletteControllerTest {
	private final CreateRouletteRest  CREATE_ROULETTE_REST = new CreateRouletteRest();
	private final ListRouletteRest ROULETTE_REST = new ListRouletteRest();
	private final String ID_STATE = "1";
	private final Long ID_ROULETTE = Long.valueOf(String.valueOf(System.currentTimeMillis()).substring(4));
	private final String DESCRIPTION_STATE = "Created";
	private final List<ListRouletteRest> LIST_ROULETTE_REST = new ArrayList<>();
	private StateDTO STATE_ROULETTE_DTO = null;
	private static final String SUCCES_STATUS = "Succes";
	private static final String SUCCES_CODE = "200 OK";
	private static final String SUCCES_MESSAGE = "OK";
	private static final String SUCCES = "Exitosa";
	@Mock
	RouletteService rouletteService;
	@InjectMocks
	RouletteController rouletteController;	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);	
		Integer idRoulette = Integer.valueOf(String.valueOf(System.currentTimeMillis()).substring(4));
		STATE_ROULETTE_DTO = new StateDTO(Integer.valueOf(ID_STATE),DESCRIPTION_STATE);		
		CREATE_ROULETTE_REST.setIdState(STATE_ROULETTE_DTO);
		CREATE_ROULETTE_REST.setIdRoulette(idRoulette);
		ROULETTE_REST.setIdRoulette(idRoulette);
		LIST_ROULETTE_REST.add(ROULETTE_REST);
	}	
	@Test
	public void createRouletteTest() throws InternalServerErrorException {
		Mockito.when(rouletteService.createRoulette()).thenReturn(CREATE_ROULETTE_REST);
		ManagerApiResponse<CreateRouletteRest> response = rouletteController.createRoulette();
		assertEquals(response.getCode(), SUCCES_CODE);
		assertEquals(response.getMessage(), SUCCES_MESSAGE);
		assertEquals(response.getStatus(), SUCCES_STATUS);
		assertEquals(response.getDataInformation(), CREATE_ROULETTE_REST);	
	}
	@Test(expected = InternalServerErrorException.class)
	public void createRouletteErrorTest() throws InternalServerErrorException {
		Mockito.doThrow(InternalServerErrorException.class).when(rouletteService).createRoulette();
		ManagerApiResponse<CreateRouletteRest> response = rouletteController.createRoulette();
		fail();
	}
	@Test(expected = InternalServerErrorException.class)
	public void openingRouletteErrorTest() throws NotFoundException, InternalServerErrorException {
		Mockito.doThrow(InternalServerErrorException.class).when(rouletteService).openingRoulette(ID_ROULETTE);
		rouletteController.openingRoulette(ID_ROULETTE);
		fail();
	}
	@Test(expected = NotFoundException.class)
	public void openingRouletteErrorNotFoundTest() throws NotFoundException, InternalServerErrorException {
		Mockito.doThrow(NotFoundException.class).when(rouletteService).openingRoulette(ID_ROULETTE);
		rouletteController.openingRoulette(ID_ROULETTE);
		fail();
	}
	@Test
	public void openingRouletteTest() throws NotFoundException, InternalServerErrorException {
		Mockito.when(rouletteService.openingRoulette(ID_ROULETTE)).thenReturn(SUCCES);
		ManagerApiResponse<String> response = rouletteController.openingRoulette(ID_ROULETTE);
		assertEquals(SUCCES, response.getDataInformation());
		assertNotNull(response.getDataInformation());
	}
	@Test(expected = InternalServerErrorException.class)
	public void getRouletteListErrorTest() throws InternalServerErrorException {
		Mockito.doThrow(InternalServerErrorException.class).when(rouletteService).listRoulette();
		rouletteController.getRouletteList();		
		fail();
	}
	@Test
	public void getRouletteList() throws InternalServerErrorException {
		Mockito.when(rouletteService.listRoulette()).thenReturn(LIST_ROULETTE_REST);
		ManagerApiResponse<List<ListRouletteRest>> response = rouletteController.getRouletteList();
		assertEquals(response.getCode(), SUCCES_CODE);
		assertEquals(response.getMessage(), SUCCES_MESSAGE);
		assertEquals(response.getStatus(), SUCCES_STATUS);
		assertFalse(response.getDataInformation().isEmpty());
		assertEquals(response.getDataInformation().size(), 1);
	}
}
