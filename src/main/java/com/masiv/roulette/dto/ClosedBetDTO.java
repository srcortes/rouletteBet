package com.masiv.roulette.dto;

import lombok.Data;
/**
 * 
 * @author srcortes
 *
 */
@Data
public final class ClosedBetDTO {
     private int idResult;
     private CreateBetDTO createBetDTO;
     private Long idUser;
     private double earnedValue;
     private int numberGenerate;
 	 private String typeBet;
     private String dateEmission; 
}
