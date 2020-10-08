package com.masiv.roulette.service.implement;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.masiv.roulette.constant.ConstantOpeningRoulette;
import com.masiv.roulette.dictionaryerrors.DictionaryErros;
import com.masiv.roulette.dto.RouletteDTO;
import com.masiv.roulette.dto.StateDTO;
import com.masiv.roulette.exceptions.ManagerApiException;
import com.masiv.roulette.factory.FactoryState;
import com.masiv.roulette.funcionalinterface.VerifyObjectExists;
import com.masiv.roulette.json.CreateRouletteRest;
import com.masiv.roulette.json.ListRouletteRest;
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
        	log.error(DictionaryErros.ErrorInternalServer.messageInternalError + ex.getCause());
        	throw new ManagerApiException(HttpStatus.INTERNAL_SERVER_ERROR,
        			DictionaryErros.ErrorInternalServer.messageInternalError, ex);
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
        	log.error(DictionaryErros.ErrorInternalServer.messageInternalError + ex.getCause());
        	throw new ManagerApiException(HttpStatus.INTERNAL_SERVER_ERROR,
        			DictionaryErros.ErrorInternalServer.messageInternalError, ex);	
		}		
	}
	@Override
	public String openingRoulette(Long idRoulette) throws ManagerApiException {
		String messages = "";
		
			VerifyObjectExists isFound = new IntegrationUtil()::existObject;
			boolean existsCustomer = isFound.existsRow(rouletteRepository.listRoulette(), i->i.getIdRoulette() == idRoulette);
			if(!existsCustomer) {
				messages = ConstantOpeningRoulette.DENIED.getMessage();
				throw new ManagerApiException(HttpStatus.NOT_FOUND,DictionaryErros.ErrorNotFound.messageNotFoundRoulette+"-"+messages);
			}
			rouletteRepository.openingRoulette(idRoulette);
			messages = ConstantOpeningRoulette.SUCCES.getMessage();
		
		return messages;
	}
	@Override
	public List<ListRouletteRest> listRoulette() throws ManagerApiException {
		try {
			List<RouletteDTO> listRouletteComplete = rouletteRepository.listRoulette();
			return listRouletteComplete.stream().map(j-> modelMapper.map(j, ListRouletteRest.class)).collect(Collectors.toList());
		}catch(Exception ex) {
			log.error(""+ ex);
			throw new ManagerApiException(HttpStatus.INTERNAL_SERVER_ERROR,
        			DictionaryErros.ErrorInternalServer.messageInternalError);
		}		
	}
}
