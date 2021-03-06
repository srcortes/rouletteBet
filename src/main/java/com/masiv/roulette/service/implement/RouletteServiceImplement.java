package com.masiv.roulette.service.implement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.masiv.roulette.constant.ConstantColor;
import com.masiv.roulette.constant.ConstantOpeningRoulette;
import com.masiv.roulette.constant.ConstantState;
import com.masiv.roulette.dictionaryerrors.DictionaryErros;
import com.masiv.roulette.dto.ClosedBetDTO;
import com.masiv.roulette.dto.CreateBetDTO;
import com.masiv.roulette.dto.RouletteDTO;
import com.masiv.roulette.dto.StateBetDTO;
import com.masiv.roulette.dto.StateDTO;
import com.masiv.roulette.exceptions.AmountNotPermittedException;
import com.masiv.roulette.exceptions.ColorNotAllowedException;
import com.masiv.roulette.exceptions.ManagerApiException;
import com.masiv.roulette.exceptions.NotExistBetException;
import com.masiv.roulette.exceptions.NotFoundException;
import com.masiv.roulette.exceptions.NotOpenRouletteException;
import com.masiv.roulette.exceptions.NumberOutRangeException;
import com.masiv.roulette.factory.FactoryState;
import com.masiv.roulette.factory.FactoryStateBet;
import com.masiv.roulette.funcionalinterface.VerfifyAmountBet;
import com.masiv.roulette.funcionalinterface.VerifyColors;
import com.masiv.roulette.funcionalinterface.VerifyIsNumber;
import com.masiv.roulette.funcionalinterface.VerifyNumberRange;
import com.masiv.roulette.funcionalinterface.VerifyObjectExists;
import com.masiv.roulette.json.BetUserRest;
import com.masiv.roulette.json.ClosedBetRest;
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
        		 this.createStateBet();
        		 create = Boolean.TRUE;
        	 }
        	 RouletteDTO roulette = new RouletteDTO();
        	 roulette.setIdRoulette(IntegrationUtil.generateKey());
        	 roulette.setIdState(FactoryState.createFirtState());
        	 rouletteRepository.createRoulette(roulette);
        	 return modelMapper.map(roulette, CreateRouletteRest.class);        	
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
	public void createStateBet() throws ManagerApiException {
		try {
			StateBetDTO stateBetDTO[] = { FactoryStateBet.createFirtState(), FactoryStateBet.createSecondState()};
			for (StateBetDTO s : stateBetDTO)
				rouletteRepository.createStateBet(s);
		} catch (Exception ex) {
			log.error(DictionaryErros.ERROR_INTERNAL_SERVER.getDescriptionError() + ex);
			throw new ManagerApiException(HttpStatus.INTERNAL_SERVER_ERROR,
					DictionaryErros.ERROR_INTERNAL_SERVER.getDescriptionError(), ex);
		}
	}
	@Override
	public String changeStateRoulette(Long idRoulette) throws Exception {		
		String messages = "";
		try {
			VerifyObjectExists isFound = new IntegrationUtil()::existObject;
			VerifyObjectExists isClosed = new IntegrationUtil()::existObject;
			boolean existsCustomer = isFound.existsRow(rouletteRepository.listRoulette(),
					i -> i.getIdRoulette() == idRoulette);
			if (!existsCustomer) {				
				messages = ConstantOpeningRoulette.DENIED.getMessage();
				throw new NotFoundException(HttpStatus.NOT_FOUND,null);
			}
			boolean roulettedIsClosed = isClosed.existsRow(rouletteRepository.listRoulette(),
					i -> i.getIdRoulette() == idRoulette
							&& i.getIdState().getDescription().equalsIgnoreCase(ConstantState.CLOSED.getName()));
			if(roulettedIsClosed)
				throw new NotOpenRouletteException(HttpStatus.NOT_FOUND,null);
				
			rouletteRepository.changeStateRoulette(idRoulette, Integer.valueOf(ConstantState.OPENING.getId()));
			messages = ConstantOpeningRoulette.SUCCES.getMessage();
		}catch (NotFoundException ex) {           
        	   log.error(DictionaryErros.ERROR_NOT_FOUND.getDescriptionError());
        	   throw new NotFoundException(HttpStatus.NOT_FOUND,DictionaryErros.ERROR_NOT_FOUND.getDescriptionError());         
        }catch(NotOpenRouletteException e) {
        	throw new ManagerApiException(HttpStatus.INTERNAL_SERVER_ERROR,
        			   DictionaryErros.ROULETTE_ISCLOSED.getDescriptionError());
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
	@Override
	public List<ClosedBetRest> closedBet(Long idRoulette) throws Exception {
		try {
			VerifyObjectExists isFound = new IntegrationUtil()::existObject;
			VerifyObjectExists isOpeningRoulette = new IntegrationUtil()::existObject;
			int numberRandom = IntegrationUtil.generateNumberRandom();
			boolean existsRoulette = isFound.existsRow(rouletteRepository.listRoulette(),
					i -> i.getIdRoulette() == idRoulette);
			if (!existsRoulette) 			
				throw new NotFoundException(HttpStatus.NOT_FOUND, null);			
			boolean existsRouletteOpening = isOpeningRoulette.existsRow(rouletteRepository.listRoulette(),
					i -> i.getIdRoulette() == idRoulette && i.getIdState().getIdState() == Integer.valueOf(ConstantState.OPENING.getId()));
			if(!existsRouletteOpening)
				throw new NotOpenRouletteException(HttpStatus.EXPECTATION_FAILED, null);
			if(rouletteRepository.existsBetByRoulette(idRoulette).size() == 0)
				throw new NotExistBetException(HttpStatus.EXPECTATION_FAILED, null);
			rouletteRepository.changeStateRoulette(idRoulette, Integer.valueOf(ConstantState.CLOSED.getId()));
			rouletteRepository.closedBet(idRoulette);
			List<ClosedBetDTO> listWinner = rouletteRepository.selectPersonWinner(idRoulette,numberRandom);			
			
			return this.getPersonWinner(listWinner);
		} catch (NotFoundException ex) {
			log.error(DictionaryErros.ERROR_NOT_FOUND.getDescriptionError());
			throw new NotFoundException(HttpStatus.NOT_FOUND, DictionaryErros.ERROR_NOT_FOUND.getDescriptionError());
		} catch(NotOpenRouletteException ex) {
			throw new NotOpenRouletteException(HttpStatus.EXPECTATION_FAILED,
					DictionaryErros.NOT_ROULETTE_OPENING.getDescriptionError());
		}catch (NotExistBetException e) {
			throw new NotExistBetException(HttpStatus.INTERNAL_SERVER_ERROR,
					DictionaryErros.NOT_EXISTS_BET_FOR_ROULETTE.getDescriptionError());
		}catch (ManagerApiException e) {
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
		createBetDTO.setBet(createBetRest.getBet().toLowerCase());
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
	public List<ClosedBetRest> getPersonWinner(List<ClosedBetDTO> listWinnerBet) throws ManagerApiException {
		try {
			Iterator<ClosedBetDTO> iterator = listWinnerBet.iterator();
			List<ClosedBetDTO> newListMapRest = new ArrayList<>();
			while (iterator.hasNext()) {
				ClosedBetDTO closedBetOld = (ClosedBetDTO) iterator.next();
				ClosedBetDTO closedBetNew = new ClosedBetDTO();		
				closedBetNew.setIdResult(IntegrationUtil.generateKey());
				if (closedBetOld.getCreateBetDTO().getBet().equalsIgnoreCase(ConstantColor.NEGRO.getColors())
						|| closedBetOld.getCreateBetDTO().getBet().equalsIgnoreCase(ConstantColor.ROJO.getColors())) 
					closedBetNew.setEarnedValue(closedBetOld.getCreateBetDTO().getAmount() * 1.8);               
				else
					closedBetNew.setEarnedValue(closedBetOld.getCreateBetDTO().getAmount() * 5);
				closedBetNew.setCreateBetDTO(closedBetOld.getCreateBetDTO());
				closedBetNew.setIdUser(closedBetOld.getCreateBetDTO().getIdUser());
				closedBetNew.setNumberGenerate(closedBetOld.getNumberGenerate());
				closedBetNew.setTypeBet(closedBetOld.getCreateBetDTO().getBet());
				closedBetNew.setDateEmission(IntegrationUtil.getDateToday());
				newListMapRest.add(closedBetNew);
				rouletteRepository.createFinalScore(closedBetNew);
			}
			return newListMapRest.stream().map(j-> modelMapper.map(j, ClosedBetRest.class)).collect(Collectors.toList());		

		} catch (ManagerApiException ex) {
			log.error(DictionaryErros.ERROR_INTERNAL_SERVER.getDescriptionError() + ex);
			throw new ManagerApiException(HttpStatus.INTERNAL_SERVER_ERROR,
					DictionaryErros.ERROR_INTERNAL_SERVER.getDescriptionError());
		}	
	}
}
