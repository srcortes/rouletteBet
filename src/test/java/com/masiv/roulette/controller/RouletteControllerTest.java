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
/**
 * 
 * @author srcortes
 *
 */
public class RouletteControllerTest {
	private final CreateRouletteRest  CREATE_ROULETTE_REST = new CreateRouletteRest();
	private final ListRouletteRest ROULETTE_REST = new ListRouletteRest();
	private final BetUserRest BET_USER_REST = new BetUserRest();
	private final CreateBetRest CREATE_BET_REST = new CreateBetRest();
	private final Long ID_USER = new Long(8021778001L);
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
		Integer id = Integer.valueOf(String.valueOf(System.currentTimeMillis()).substring(4));
		STATE_ROULETTE_DTO = new StateDTO(Integer.valueOf(ID_STATE),DESCRIPTION_STATE);		
		CREATE_ROULETTE_REST.setIdState(STATE_ROULETTE_DTO);
		CREATE_ROULETTE_REST.setIdRoulette(id);
		ROULETTE_REST.setIdRoulette(id);
		LIST_ROULETTE_REST.add(ROULETTE_REST);
		BET_USER_REST.setIdBet(id);
		BET_USER_REST.setIdUser(6270561l);
		BET_USER_REST.setBet("36");
		BET_USER_REST.setAmount(7000d);
		CREATE_BET_REST.setAmount("7000");
		CREATE_BET_REST.setBet("36");
		
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
		fail();
	}	
	@Test(expected = ManagerApiException.class)
	public void openingRouletteErrorErrorTest() throws Exception {
		Mockito.doThrow(ManagerApiException.class).when(rouletteService).openingRoulette(ID_ROULETTE);
		rouletteController.openingRoulette(ID_ROULETTE);
		fail();
	}
	@Test
	public void openingRouletteTest() throws Exception {
		Mockito.when(rouletteService.openingRoulette(ID_ROULETTE)).thenReturn(SUCCES);
		ManagerApiResponse<String> response = rouletteController.openingRoulette(ID_ROULETTE);
		assertEquals(SUCCES, response.getDataInformation());
		assertNotNull(response.getDataInformation());
	}
	@Test(expected = ManagerApiException.class)
	public void getRouletteListErrorTest() throws ManagerApiException {
		Mockito.doThrow(ManagerApiException.class).when(rouletteService).listRoulette();
		rouletteController.getRouletteList();		
		fail();
	}
	@Test
	public void getRouletteList() throws ManagerApiException {
		Mockito.when(rouletteService.listRoulette()).thenReturn(LIST_ROULETTE_REST);
		ManagerApiResponse<List<ListRouletteRest>> response = rouletteController.getRouletteList();
		assertEquals(response.getCode(), SUCCES_CODE);
		assertEquals(response.getMessage(), SUCCES_MESSAGE);
		assertEquals(response.getStatus(), SUCCES_STATUS);
		assertFalse(response.getDataInformation().isEmpty());
		assertEquals(response.getDataInformation().size(), 1);
	}
	@Test
	public void generateBetTest()throws Exception {
		Mockito.when(rouletteService.listBetUser(CREATE_BET_REST, ID_USER)).thenReturn(BET_USER_REST);
		ManagerApiResponse<BetUserRest> response = rouletteController.generateBet(CREATE_BET_REST, ID_USER);
		assertEquals(response.getCode(), SUCCES_CODE);
		assertEquals(response.getMessage(), SUCCES_MESSAGE);
		assertEquals(response.getStatus(), SUCCES_STATUS);
		assertEquals(response.getDataInformation(), BET_USER_REST);
	}
	@Test(expected = ManagerApiException.class)
	public void generateBetErrorTest()throws Exception{
		Mockito.doThrow(ManagerApiException.class).when(rouletteService).listBetUser(CREATE_BET_REST, ID_USER);
		rouletteController.generateBet(CREATE_BET_REST, ID_USER);
		fail();
		
	}
}
