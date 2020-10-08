package com.masiv.roulette.funcionalinterface;

import java.util.List;
import java.util.function.Predicate;

import com.masiv.roulette.exceptions.InternalServerErrorException;
import com.masiv.roulette.exceptions.ManagerApiException;

/**
 * 
 * @author srcortes
 *
 */
@FunctionalInterface
public interface VerifyObjectExists {
	<T> boolean existsRow(List<T> t, Predicate<T> predicate) throws InternalServerErrorException;
}