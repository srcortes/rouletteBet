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
	public ResponseEntity<Object> customHandleNotFound(final Exception ex, WebRequest request) {
		ManagerApiException finEx = (ManagerApiException) ex;
		ApiError apiError = new ApiError(finEx.getStatus(), "There are data with errors", ex.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
}
