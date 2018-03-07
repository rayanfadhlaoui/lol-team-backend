package com.lolteam.utils;

import com.lolteam.utils.exceptions.ConvertionException;

public class NumberConvertor {

	public static Integer longToInt(Long longValue) {
		try{
			return Math.toIntExact(longValue);
		}
		catch (ArithmeticException e) {
			throw new ConvertionException(longValue + " is too big to be converted to Integer");
		}
	}

}
