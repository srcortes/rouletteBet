package com.masiv.roulette.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.masiv.roulette.dto.RouletteDTO;
import com.masiv.roulette.dto.StateDTO;
import com.masiv.roulette.exceptions.ManagerApiException;
import com.masiv.roulette.json.CreateRouletteRest;
import com.masiv.roulette.json.ListRouletteRest;
import com.masiv.roulette.repositories.RouletteRepository;
import com.masiv.roulette.service.implement.RouletteServiceImplement;
/**
 * 
 * @author srcortes
 *
 */
public class RouletteServiceTest {
	private final RouletteDTO ROULETTE_DTO = new RouletteDTO();
	private final RouletteDTO ROULETTE_DTO_RESPONSE = new RouletteDTO();
	private final ListRouletteRest ROULETTE_REST = new ListRouletteRest();
	private final CreateRouletteRest  CREATE_ROULETTE_REST = new CreateRouletteRest();
	private final List<RouletteDTO> LIST_ROULETTE_DTO = new ArrayList<>();
	private final List<ListRouletteRest> LIST_ROULETTE_REST = new ArrayList<>();
	private StateDTO STATE_ROULETTE_DTO = null;
	private final String SUCCES = "Exitosa"; 
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
		CREATE_ROULETTE_REST.setIdState(ROULETTE_DTO.getIdState());
		LIST_ROULETTE_DTO.add(ROULETTE_DTO);
		ROULETTE_REST.setIdRoulette(primaryKey);
		ROULETTE_REST.setIdState(STATE_ROULETTE_DTO);
		LIST_ROULETTE_REST.add(ROULETTE_REST);
	}
	@Test
	public void createStateRouletteTest() throws ManagerApiException {
		Mockito.doNothing().when(rouletteRepository).createStateRoulette(STATE_ROULETTE_DTO);
		rouletteServiceImplement.createStateRoulette();		
	}
	@Test(expected = ManagerApiException.class)
	public void createStateRouletteErrorTest() throws ManagerApiException {
		Mockito.doThrow(ManagerApiException.class).when(rouletteRepository).createStateRoulette(STATE_ROULETTE_DTO);
		rouletteServiceImplement.createStateRoulette();
		fail();	
	}
	@Test
	public void createRouletteTest() throws ManagerApiException {
		Mockito.when(rouletteRepository.createRoulette(ROULETTE_DTO)).thenReturn(ROULETTE_DTO);
		assertEquals(CREATE_ROULETTE_REST, rouletteServiceImplement.createRoulette());
	}
	@Test(expected = ManagerApiException.class)
	public void createRouletteErrorTest() throws ManagerApiException{
		Mockito.doThrow(ManagerApiException.class).when(rouletteRepository).createRoulette(ROULETTE_DTO_RESPONSE);
		rouletteServiceImplement.createRoulette();
		fail();
	}	
	@Test(expected = ManagerApiException.class)
	public void openingRouletteErrorInternalTest() throws ManagerApiException {
		Mockito.doThrow(ManagerApiException.class).when(rouletteRepository).openingRoulette(ROULETTE_DTO.getIdRoulette());
		Mockito.doThrow(ManagerApiException.class).when(rouletteRepository).listRoulette();
		rouletteServiceImplement.openingRoulette(ROULETTE_DTO.getIdRoulette());
		fail();
	}
	@Test(expected = ManagerApiException.class)
	public void openingRouletteNotFoundTest() throws ManagerApiException {
		Mockito.doNothing().when(rouletteRepository).openingRoulette(ROULETTE_DTO.getIdRoulette());
		Mockito.doThrow(ManagerApiException.class).when(rouletteRepository).listRoulette();
		rouletteServiceImplement.openingRoulette(ROULETTE_DTO.getIdRoulette());
		fail();
	}
	@Test
	public void openingRoulette() throws ManagerApiException{
		Mockito.doNothing().when(rouletteRepository).openingRoulette(ROULETTE_DTO.getIdRoulette());
		Mockito.when(rouletteRepository.listRoulette()).thenReturn(LIST_ROULETTE_DTO);
		String messages = rouletteServiceImplement.openingRoulette(ROULETTE_DTO.getIdRoulette());
		assertEquals(messages, SUCCES);
	}
	@Test
	public void listRouletteTest() throws ManagerApiException{
		Mockito.when(rouletteRepository.listRoulette()).thenReturn(LIST_ROULETTE_DTO);
		rouletteServiceImplement.listRoulette();
		assertEquals(rouletteServiceImplement.listRoulette(), LIST_ROULETTE_REST);
		assertFalse(rouletteServiceImplement.listRoulette().isEmpty());
		assertEquals(rouletteServiceImplement.listRoulette().size(),1);
	}
	@Test(expected = ManagerApiException.class)
	public void listRouletteErrorTest() throws ManagerApiException{
		Mockito.doThrow(ManagerApiException.class).when(rouletteRepository).listRoulette();
		rouletteServiceImplement.listRoulette();
		fail();		
	}
}
