package com.lolteam.tests.assertions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListAssertion<T> {
	private final List<T> actualList;

	public ListAssertion(List<T> list) {
		actualList = list;
	}

	public void contains(@SuppressWarnings("unchecked") T... expectedResults) {
		Stream.of(expectedResults).forEach(expectedResult -> assertTrue(actualList.contains(expectedResult), displayError(expectedResult)));
	}

	private String displayError(T expectedResult) {
		String errorMessage = "\n'" + expectedResult + "' is not in :\n[";
		errorMessage += actualList.stream().map(s -> s != null ? "'"+s.toString()+"'": "'null'").collect(Collectors.joining(", "));
		return errorMessage+"]";
	}

}
