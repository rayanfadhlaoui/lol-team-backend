package com.lolteam.tests.assertions;

import java.util.List;

public class LTAssertions {

	public static <T> ListAssertion <T> assertThat(List<T> list) {
		return new ListAssertion<>(list);
	}

}
