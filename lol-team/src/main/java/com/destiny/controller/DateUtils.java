package com.destiny.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

	/**
	 * @return if the date was successfully parsed, return the date<br/>
	 *         if not, returns null
	 */
	public static Date parse(String date) {
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

}
