package com.masiv.roulette.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.masiv.roulette.response.ManagerApiResponse;
import com.masiv.roulette.service.RouletteService;
import com.masiv.roulette.exceptions.ManagerApiException;
import com.masiv.roulette.json.CreateRouletteRest;
import com.masiv.roulette.json.ListRouletteRest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * This is a controller for roulette 
 * @author srcortes
 */
@RestController
@RequestMapping(produces = "application/json")
@Validated
@CrossOrigin(origins = "*")
public class RouletteController {
	@Autowired
	RouletteService rouletteService;	
	@ApiOperation(notes = "Service is responsable of the creation of a roulette", value = "N/A")
	@ApiResponses({ @ApiResponse(code = 200, message = "Ok", response = CreateRouletteRest.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ManagerApiException.class) })
	@PostMapping(value = "/createRoulette")
	public ManagerApiResponse<CreateRouletteRest> createRoulette() throws ManagerApiException{
		
		return new ManagerApiResponse<>("Succes", String.valueOf(HttpStatus.OK), "OK",
				rouletteService.createRoulette());
	}
	@ApiOperation(notes = "Service is responsable for opening a roulette of agree a id", value = "Id Roulette")
	@ApiResponses({ @ApiResponse(code = 200, message = "Ok", response = String.class),
		@ApiResponse(code = 500, message = "Internal Server Error", response = ManagerApiException.class) })
	@PutMapping(value = "/openingRoulette/{idRoulette}")
	public ManagerApiResponse<String> openingRoulette(
			@ApiParam(value = "Id roulette created earlier", required = true) @PathVariable("idRoulette") Long idRoulette)
			throws ManagerApiException{

		return new ManagerApiResponse<>("Succes", String.valueOf(HttpStatus.OK), "OK",
				rouletteService.openingRoulette(idRoulette));
	}
	@ApiOperation(notes = "Service is responsable of make a list of roulette with state", value = "N/A")
	@ApiResponses({ @ApiResponse(code = 200, message = "Ok", response = CreateRouletteRest.class),
		@ApiResponse(code = 500, message = "Internal Server Error", response = ManagerApiException.class) })
	@GetMapping(value = "/rouletteList")
	public ManagerApiResponse<List<ListRouletteRest>> getRouletteList() throws ManagerApiException{
		
		return new ManagerApiResponse<>("Succes", String.valueOf(HttpStatus.OK), "OK",
				rouletteService.listRoulette());		
	}
}
