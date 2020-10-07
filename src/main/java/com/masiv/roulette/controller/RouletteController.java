package com.masiv.roulette.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masiv.roulette.response.ManagerApiResponse;
import com.masiv.roulette.service.RouletteService;
import com.masiv.roulette.exceptions.ManagerApiException;
import com.masiv.roulette.json.CreateRouletteRest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

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
	public static final ModelMapper modelMapper = new ModelMapper();
	@ApiOperation(notes = "Service is responsable of the creation of a roulette", value = "N/A")
	@ApiResponses({ @ApiResponse(code = 200, message = "Ok", response = Integer.class),
			@ApiResponse(code = 400, message = "Internal Server Error", response = ManagerApiException.class) })
	@PostMapping(value = "/createRoulette")
	public ManagerApiResponse<CreateRouletteRest> createRoulette() throws ManagerApiException{
		return new ManagerApiResponse<>("Succes", String.valueOf(HttpStatus.OK), "OK",
				rouletteService.createRoulette());
	}

}
