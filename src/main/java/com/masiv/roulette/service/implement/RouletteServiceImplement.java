package com.masiv.roulette.service.implement;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.masiv.roulette.constant.ConstantOpeningRoulette;
import com.masiv.roulette.dictionaryerrors.DictionaryErros;
import com.masiv.roulette.dto.CreateBetDTO;
import com.masiv.roulette.dto.RouletteDTO;
import com.masiv.roulette.dto.StateDTO;
import com.masiv.roulette.exceptions.ManagerApiException;
import com.masiv.roulette.factory.FactoryState;
import com.masiv.roulette.funcionalinterface.VerfifyAmountBet;
import com.masiv.roulette.funcionalinterface.VerifyColors;
import com.masiv.roulette.funcionalinterface.VerifyIsNumber;
import com.masiv.roulette.funcionalinterface.VerifyNumberRange;
import com.masiv.roulette.funcionalinterface.VerifyObjectExists;
import com.masiv.roulette.json.BetUserRest;
import com.masiv.roulette.json.CreateBetRest;
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
        	log.error(DictionaryErros.ERROR_INTERNAL_SERVER.getDescriptionError() + ex.getCause());
        	throw new ManagerApiException(HttpStatus.INTERNAL_SERVER_ERROR,
        			DictionaryErros.ERROR_INTERNAL_SERVER.getDescriptionError(), ex);
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
        	log.error(DictionaryErros.ERROR_INTERNAL_SERVER.getDescriptionError() + ex.getCause());
        	throw new ManagerApiException(HttpStatus.INTERNAL_SERVER_ERROR,
        			DictionaryErros.ERROR_INTERNAL_SERVER.getDescriptionError(), ex);	
		}		
	}	
	@Override
	public String openingRoulette(Long idRoulette) throws ManagerApiException {
		HttpStatus httpStatus = null;
		String messages = "";
		try {
			VerifyObjectExists isFound = new IntegrationUtil()::existObject;
			boolean existsCustomer = isFound.existsRow(rouletteRepository.listRoulette(),
					i -> i.getIdRoulette() == idRoulette);
			if (!existsCustomer) {
				httpStatus = HttpStatus.NOT_FOUND;
				messages = ConstantOpeningRoulette.DENIED.getMessage();
				throw new ManagerApiException(HttpStatus.NOT_FOUND,null);
			}
			rouletteRepository.openingRoulette(idRoulette);
			messages = ConstantOpeningRoulette.SUCCES.getMessage();
		} catch (Exception ex) {
           if(httpStatus.equals(HttpStatus.NOT_FOUND)) {
        	   log.error(DictionaryErros.ERROR_NOT_FOUND.getDescriptionError());
        	   throw new ManagerApiException(HttpStatus.NOT_FOUND,DictionaryErros.ERROR_NOT_FOUND.getDescriptionError());
           }else {
        	   throw new ManagerApiException(HttpStatus.INTERNAL_SERVER_ERROR,
        			   DictionaryErros.ERROR_INTERNAL_SERVER.getDescriptionError());
           }
		}
		
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
					DictionaryErros.ERROR_INTERNAL_SERVER.getDescriptionError());
		}		
	}	
	@Override
	public BetUserRest listBetUser(CreateBetRest createBetRest,Long idUser) throws ManagerApiException {
		HttpStatus httpStatus = null;
		try {
			VerifyIsNumber verifyIsNumber = new IntegrationUtil()::isNumber;						
			if (verifyIsNumber.isNumber(createBetRest.getBet())) {
				VerifyNumberRange verifyNumberRange = new IntegrationUtil()::validatedNumber;
				if(!verifyNumberRange.isNumberOutRange(Integer.valueOf(createBetRest.getBet()))) 									
					throw new ManagerApiException(httpStatus = HttpStatus.EXPECTATION_FAILED, null);								
			}else {
				VerifyColors verifyColors =  new IntegrationUtil()::validatedColor;
				if(!verifyColors.validatedColor(createBetRest.getBet())) 									
					throw new ManagerApiException(httpStatus = HttpStatus.CONFLICT, null);				
			}
			VerfifyAmountBet verfifyAmountBet = new IntegrationUtil()::isPermittedAmount;
			if(!verfifyAmountBet.isPermittedAmount(Long.valueOf(createBetRest.getAmount())))
				throw new ManagerApiException(httpStatus = HttpStatus.UNAUTHORIZED, null);			
			CreateBetDTO createBetDTO = new CreateBetDTO();
			createBetDTO.setIdBet(IntegrationUtil.generateKey());
			createBetDTO.setIdUser(idUser);
			createBetDTO.setRoulette(null);
			createBetDTO.setBet(createBetRest.getBet());
			createBetDTO.setAmount(Double.valueOf(createBetRest.getAmount()));
			
			return modelMapper.map(rouletteRepository.generateBet(createBetDTO), BetUserRest.class);			
		} catch (ManagerApiException ex) {
			if (httpStatus.equals(HttpStatus.EXPECTATION_FAILED)) {
				throw new ManagerApiException(HttpStatus.EXPECTATION_FAILED,
						DictionaryErros.ERROR_NUMBER_OUT_RANGE.getDescriptionError());
			} else if(httpStatus.equals(HttpStatus.CONFLICT)){
				throw new ManagerApiException(HttpStatus.CONFLICT,
						DictionaryErros.ERROR_COLORS.getDescriptionError());
			}else if(httpStatus.equals(HttpStatus.UNAUTHORIZED)) {
				throw new ManagerApiException(HttpStatus.UNAUTHORIZED,
						DictionaryErros.ERROR_EXCEEDS_LIMIT_BET.getDescriptionError());
			}else {				
				log.error(DictionaryErros.ERROR_INTERNAL_SERVER.getDescriptionError() + ex);
				throw new ManagerApiException(HttpStatus.INTERNAL_SERVER_ERROR,
						DictionaryErros.ERROR_INTERNAL_SERVER.getDescriptionError());
			}
		}		
	}	
}
