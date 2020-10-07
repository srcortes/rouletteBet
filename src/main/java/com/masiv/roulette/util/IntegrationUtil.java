package com.masiv.roulette.util;

import java.security.SecureRandom;

public class IntegrationUtil {	
    public static Integer generateKey(){
       return Integer.valueOf(String.valueOf(System.currentTimeMillis()).substring(4));
     }
}
