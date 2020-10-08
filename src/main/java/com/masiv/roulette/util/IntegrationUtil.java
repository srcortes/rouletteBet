package com.masiv.roulette.util;

import java.security.SecureRandom;
import java.util.List;
import java.util.function.Predicate;
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
}
