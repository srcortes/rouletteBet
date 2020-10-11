package com.masiv.roulette.util;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import com.masiv.roulette.constant.ConstantColor;
import com.masiv.roulette.constant.ConstantLimitAmount;
/**
 * 
 * @author srcortes
 *
 */
public class IntegrationUtil {
	private Boolean existObject = false;
	public static Integer generateKey() {		
		return Integer.valueOf(String.valueOf(System.currentTimeMillis()).substring(4));
	}
	public <T> boolean existObject(List<T> list, Predicate<T> predicate) {		
		list.forEach(i -> {
			if (predicate.test(i))
				existObject = true;
		});

		return existObject;
	}
	public boolean isNumber(String chain) {
		boolean isNumber = false;
		try {
			Integer.parseInt(chain);			
			isNumber =  true;
		}catch(NumberFormatException ex) {			
			isNumber =  false;
		}
		
		return isNumber;
	}
	public boolean validatedNumber(Integer value) {
		List<Integer> validNumbers = new ArrayList<>();		
		for(int j = 0; j <= 36; j++) 
			validNumbers.add(j);
		boolean response = validNumbers.contains(value);
		
		return response;		
	}
	public boolean validatedColor(String chain) {
		boolean isCorrectColor = false;
		String isColorCorrect = ConstantColor.byValue(chain).toString();		
		if (isColorCorrect == "isCorrect")
			isCorrectColor = true;

		return isCorrectColor;
	}
	public boolean isPermittedAmount(String amount) {
		String amountValueInString = amount.replace(".", "");
		Double amountBet = Double.valueOf(amountValueInString);		
		boolean isNotPermitted = true;
		if(amountBet > Double.valueOf(ConstantLimitAmount.LIMIT_AMOUNT.getAmount()))
			isNotPermitted =  false;
		
		return isNotPermitted;
	}
	public static Integer generateNumberRandom() {
		Random rand = new Random();
		return rand.nextInt(36);
	}
	public static String getDateToday() {
		LocalDateTime now = LocalDateTime.now();
		return now.toString();
	}
}
