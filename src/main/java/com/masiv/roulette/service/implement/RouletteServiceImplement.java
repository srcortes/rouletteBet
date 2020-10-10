package com.masiv.roulette.service.implement;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.masiv.roulette.constant.ConstantOpeningRoulette;
import com.masiv.roulette.constant.ConstantState;
import com.masiv.roulette.dictionaryerrors.DictionaryErros;
import com.masiv.roulette.dto.CreateBetDTO;
import com.masiv.roulette.dto.RouletteDTO;
import com.masiv.roulette.dto.StateDTO;
import com.masiv.roulette.exceptions.AmountNotPermittedException;
import com.masiv.roulette.exceptions.ColorNotAllowedException;
import com.masiv.roulette.exceptions.ManagerApiException;
import com.masiv.roulette.exceptions.NotFoundException;
import com.masiv.roulette.exceptions.NotOpenRouletteException;
import com.masiv.roulette.exceptions.NumberOutRangeException;
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
		}catch(ManagerApiException ex) {			
        	log.error(DictionaryErros.ERROR_INTERNAL_SERVER.getDescriptionError() + ex.getCause());
        	throw new ManagerApiException(HttpStatus.INTERNAL_SERVER_ERROR,
        			DictionaryErros.ERROR_INTERNAL_SERVER.getDescriptionError(), ex);	
		}		
	}	
	@Override
	public String openingRoulette(Long idRoulette) throws Exception {		
		String messages = "";
		try {
			VerifyObjectExists isFound = new IntegrationUtil()::existObject;
			boolean existsCustomer = isFound.existsRow(rouletteRepository.listRoulette(),
					i -> i.getIdRoulette() == idRoulette);
			if (!existsCustomer) {				
				messages = ConstantOpeningRoulette.DENIED.getMessage();
				throw new NotFoundException(HttpStatus.NOT_FOUND,null);
			}
			rouletteRepository.openingRoulette(idRoulette);
			messages = ConstantOpeningRoulette.SUCCES.getMessage();
		}catch (NotFoundException ex) {           
        	   log.error(DictionaryErros.ERROR_NOT_FOUND.getDescriptionError());
        	   throw new NotFoundException(HttpStatus.NOT_FOUND,DictionaryErros.ERROR_NOT_FOUND.getDescriptionError());         
        }catch(ManagerApiException e) {
        	throw new ManagerApiException(HttpStatus.INTERNAL_SERVER_ERROR,
       			   DictionaryErros.ERROR_INTERNAL_SERVER.getDescriptionError());
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
	public BetUserRest listBetUser(CreateBetRest createBetRest,Long idUser) throws Exception {		
		try {			
			VerifyObjectExists existsRouletteOpen = new IntegrationUtil()::existObject;
			VerifyIsNumber verifyIsNumber = new IntegrationUtil()::isNumber;
			VerfifyAmountBet verfifyAmountBet = new IntegrationUtil()::isPermittedAmount;
			boolean rouletteOpen = existsRouletteOpen.existsRow(rouletteRepository.listRoulette(),
					i -> i.getIdState().getDescription().equalsIgnoreCase(ConstantState.OPENING.getName()));
			if(!rouletteOpen)
				throw new NotOpenRouletteException(HttpStatus.EXPECTATION_FAILED, null);
			if (verifyIsNumber.isNumber(createBetRest.getBet())) {
				VerifyNumberRange verifyNumberRange = new IntegrationUtil()::validatedNumber;
				if(!verifyNumberRange.isNumberOutRange(Integer.valueOf(createBetRest.getBet()))) 									
					throw new NumberOutRangeException(HttpStatus.EXPECTATION_FAILED, null);								
			}else {
				VerifyColors verifyColors =  new IntegrationUtil()::validatedColor;
				if(!verifyColors.validatedColor(createBetRest.getBet())) 									
					throw new ColorNotAllowedException(HttpStatus.EXPECTATION_FAILED, null);				
			}			
			if(!verfifyAmountBet.isPermittedAmount(createBetRest.getAmount()))
				throw new AmountNotPermittedException(HttpStatus.EXPECTATION_FAILED, null);			
			
			return this.createBet(createBetRest, idUser);		
		}catch (NotOpenRouletteException ex) {
			log.error("" + ex);
			throw new NotOpenRouletteException(HttpStatus.EXPECTATION_FAILED,
					DictionaryErros.ROULETTE_NOT_OPENING.getDescriptionError());
		}catch(ColorNotAllowedException color) {
			throw new ColorNotAllowedException(HttpStatus.CONFLICT,
					DictionaryErros.ERROR_COLORS.getDescriptionError());			
		}catch(NumberOutRangeException out) {
			throw new NumberOutRangeException(HttpStatus.CONFLICT,
					DictionaryErros.ERROR_NUMBER_OUT_RANGE.getDescriptionError());
		}catch(AmountNotPermittedException amount) {
			throw new NumberOutRangeException(HttpStatus.CONFLICT,
					DictionaryErros.ERROR_EXCEEDS_LIMIT_BET.getDescriptionError());
		}catch(ManagerApiException e)	{
			log.error("" + e);
			throw new ManagerApiException(HttpStatus.INTERNAL_SERVER_ERROR,
					DictionaryErros.ERROR_INTERNAL_SERVER.getDescriptionError());
		}
	}
	public BetUserRest createBet(CreateBetRest createBetRest,Long idUser)throws ManagerApiException{
		try {
		CreateBetDTO createBetDTO = new CreateBetDTO();
		createBetDTO.setIdBet(IntegrationUtil.generateKey());
		createBetDTO.setIdUser(idUser);
		createBetDTO.setRoulette(this.selectionRouletteOpening());
		createBetDTO.setBet(createBetRest.getBet());
		createBetDTO.setAmount(Double.valueOf(createBetRest.getAmount()));
		
		return modelMapper.map(rouletteRepository.generateBet(createBetDTO), BetUserRest.class);
		}catch (ManagerApiException ex) {
			log.error(DictionaryErros.ERROR_INTERNAL_SERVER.getDescriptionError() + ex);
			throw new ManagerApiException(HttpStatus.INTERNAL_SERVER_ERROR,
					DictionaryErros.ERROR_INTERNAL_SERVER.getDescriptionError());
		}		
	}
	public RouletteDTO selectionRouletteOpening()throws ManagerApiException{		
		try {
			List<RouletteDTO> listRouletteComplete = rouletteRepository.listRoulette();
			RouletteDTO rouletteDTO = new RouletteDTO();
			Iterator<RouletteDTO> it = listRouletteComplete.iterator();
			while (it.hasNext()) {
				RouletteDTO roulette = (RouletteDTO) it.next();
				if (roulette.getIdState().getDescription().equalsIgnoreCase(ConstantState.OPENING.getName())) {
					rouletteDTO.setIdRoulette(roulette.getIdRoulette());
					rouletteDTO.setIdState(roulette.getIdState());
					break;
				}			
			}
			
			return rouletteDTO;			
		}catch(Exception ex) {			
				log.error(""+ ex);
				throw new ManagerApiException(HttpStatus.INTERNAL_SERVER_ERROR,
						DictionaryErros.ERROR_INTERNAL_SERVER.getDescriptionError());						
		}
	}
}
