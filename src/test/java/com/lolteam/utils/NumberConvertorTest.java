package com.lolteam.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.lolteam.utils.exceptions.ConvertionException;


class NumberConvertorTest {

	@Test
	@DisplayName("Test a simple long convertion to Integer")
	void simpleLongToInt() {
		assertEquals(5, NumberConvertor.longToInt(5l).intValue());
	}
	
	@Test
	@DisplayName("Test huge long convertion to Integer")
	void hugeLongToInt() {
		assertThrows(ConvertionException.class, () -> NumberConvertor.longToInt(35687844511l));
	}

}
