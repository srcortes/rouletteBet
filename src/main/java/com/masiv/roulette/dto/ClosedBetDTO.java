package com.masiv.roulette.dto;

import java.io.Serializable;

import lombok.Data;
/**
 * 
 * @author srcortes
 *
 */
@Data
public final class ClosedBetDTO implements Serializable {
     private int idResult;
     private CreateBetDTO createBetDTO;
     private Long idUser;
     private double earnedValue;
     private int numberGenerate;
 	 private String typeBet;
     private String dateEmission; 
}
