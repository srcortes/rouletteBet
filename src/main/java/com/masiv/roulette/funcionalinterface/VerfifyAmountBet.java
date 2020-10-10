package com.masiv.roulette.funcionalinterface;
/**
 * 
 * @author srcortes
 *
 */
@FunctionalInterface
public interface VerfifyAmountBet {
     boolean isPermittedAmount(String valueAmount);
}
