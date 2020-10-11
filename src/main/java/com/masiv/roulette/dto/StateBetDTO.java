package com.masiv.roulette.dto;
import java.io.Serializable;

import lombok.Data;
/**
 * 
 * @author srcortes
 *
 */

@Data
public final class StateBetDTO implements Serializable{
  private int idStateBet;
  private String description;
  public StateBetDTO(int idStateBet, String description) {
	  this.idStateBet = idStateBet;
	  this.description = description;
  }
}
