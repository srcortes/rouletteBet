package com.masiv.roulette.exceptions;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
/**
 * 
 * @author srcortes
 *
 */
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		List<String> error = new ArrayList<String>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			error.add(violation.getPropertyPath() + ": " + violation.getMessage());
		}
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "There are data with errors", error);
		
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> error = new ArrayList<>();
		ex.getBindingResult().getFieldErrors().stream()
				.forEach(f -> error.add(f.getField() + ":" + f.getDefaultMessage()));
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "There are data with errors", error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	@ExceptionHandler(ManagerApiException.class)
	public ResponseEntity<Object> customHandleErrorGeneric(final Exception ex, WebRequest request) {
		ManagerApiException finEx = (ManagerApiException) ex;
		ApiError apiError = new ApiError(finEx.getStatus(), "Is present error ", ex.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> customHandleNotFound(final Exception ex, WebRequest request) {
		NotFoundException finEx = (NotFoundException) ex;
		ApiError apiError = new ApiError(finEx.getStatus(), "Item not Found", ex.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	@ExceptionHandler(NotOpenRouletteException.class)
	public ResponseEntity<Object> customHandleNotOpenRoulette(final Exception ex, WebRequest request) {
		NotOpenRouletteException finEx = (NotOpenRouletteException) ex;
		ApiError apiError = new ApiError(finEx.getStatus(), "Not exist some roulette with state (opening or created)", ex.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	@ExceptionHandler(ColorNotAllowedException.class)
	public ResponseEntity<Object> customHandleNotAllowedColor(final Exception ex, WebRequest request) {
		ColorNotAllowedException finEx = (ColorNotAllowedException) ex;
		ApiError apiError = new ApiError(finEx.getStatus(), "Color not allowed, only use Red and Black", ex.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}	
	@ExceptionHandler(NumberOutRangeException.class)
	public ResponseEntity<Object> customHandleNumberOutRange(final Exception ex, WebRequest request) {
		NumberOutRangeException finEx = (NumberOutRangeException) ex;
		ApiError apiError = new ApiError(finEx.getStatus(), "The bet for number, is only permitted numbers between 0 and 36", ex.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	@ExceptionHandler(AmountNotPermittedException.class)
	public ResponseEntity<Object> customHandleAmountNotPertmittedRange(final Exception ex, WebRequest request) {
		AmountNotPermittedException finEx = (AmountNotPermittedException) ex;
		ApiError apiError = new ApiError(finEx.getStatus(), "Amount exceded the minimud permitted", ex.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	@ExceptionHandler(NotExistBetException.class)
	public ResponseEntity<Object> customHandleNotExistBetException(final Exception ex, WebRequest request) {
		NotExistBetException finEx = (NotExistBetException) ex;
		ApiError apiError = new ApiError(finEx.getStatus(), "Not exists some bet for the roulette", ex.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
}
