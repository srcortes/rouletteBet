package com.masiv.roulette.service.implement;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.masiv.roulette.dictionaryerrors.DictionaryErros;
import com.masiv.roulette.dto.RouletteDTO;
import com.masiv.roulette.dto.StateDTO;
import com.masiv.roulette.exceptions.InternalServerErrorException;
import com.masiv.roulette.exceptions.ManagerApiException;
import com.masiv.roulette.factory.FactoryState;
import com.masiv.roulette.json.CreateRouletteRest;
import com.masiv.roulette.repositories.RouletteRepository;
import com.masiv.roulette.service.RouletteService;
import com.masiv.roulette.util.IntegrationUtil;

import lombok.extern.slf4j.Slf4j;
/**
 * This class implements the interface with method mentioned in the use case
 * 
 * @author srcortes
 *
 */
@Service
@Slf4j
public final class RouletteServiceImplement implements RouletteService{
	@Autowired
	RouletteRepository rouletteRepository;
	private static final ModelMapper modelMapper = new ModelMapper();
	private static Boolean create = Boolean.FALSE;
	@Override	
	public CreateRouletteRest createRoulette() throws ManagerApiException {		
        try {
        	 if(!create) {
        		 this.createStateRoulette();
        		 create = Boolean.TRUE;
        	 }
        	 RouletteDTO roulette = new RouletteDTO();
        	 roulette.setIdRoulette(IntegrationUtil.generateKey());
        	 roulette.setIdState(FactoryState.createFirtState());       	 
        	 return modelMapper.map(rouletteRepository.createRoulette(roulette), CreateRouletteRest.class);        	
        }catch (Exception ex) {
        	ex.printStackTrace();
        	throw new InternalServerErrorException(DictionaryErros.ErrorInternalServer.messageInternalError,
        			DictionaryErros.ErrorInternalServer.messageInternalError);
        }		
	}
	@Override
	public void createStateRoulette() throws ManagerApiException {
		try {
			StateDTO state[] = {
					FactoryState.createFirtState(),
					FactoryState.createSecondState(),
					FactoryState.createThirdState(),
			};			
			for(StateDTO s : state)			
				rouletteRepository.createStateRoulette(s);					
		}catch(Exception ex) {
			log.info("", ex);
        	throw new InternalServerErrorException(DictionaryErros.ErrorInternalServer.messageInternalError,
        			DictionaryErros.ErrorInternalServer.messageInternalError);			
		}		
	}
}
