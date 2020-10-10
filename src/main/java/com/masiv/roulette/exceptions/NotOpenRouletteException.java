package com.masiv.roulette.exceptions;
import org.springframework.http.HttpStatus;
import lombok.Data;
/**
 * 
 * @author srcortes
 *
 */
@Data
public class NotOpenRouletteException  extends Exception {
	private HttpStatus status;	
	private static final long serialVersionUID = 1L;
	public NotOpenRouletteException(HttpStatus status, String message, Throwable cause) {
		super(message, cause);
		this.status = status;
	}
	public NotOpenRouletteException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}	
}
