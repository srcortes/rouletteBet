package com.masiv.roulette.dto;
import lombok.Data;
/**
 * 
 * @author srcortes
 *
 */

@Data
public final class StateBetDTO {
  private int idStateBet;
  private String description;
  public StateBetDTO(int idStateBet, String description) {
	  this.idStateBet = idStateBet;
	  this.description = description;
  }
}
