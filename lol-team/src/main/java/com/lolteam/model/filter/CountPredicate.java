package com.lolteam.model.filter;

import java.util.function.Predicate;

class CountPredicate<T> implements Predicate<T>{

	public T previousValue = null;
	public int valueCount;
	private int nbCountExpected;

	public CountPredicate(int nbCountExpected) {
		this.nbCountExpected = nbCountExpected;
	}

	@Override
	public boolean test(T value) {
		if(previousValue != null && previousValue.equals(value)) {
			valueCount++;
		}
		else {
			valueCount = 1;
			previousValue = value;
		}
		
		return valueCount == nbCountExpected;
	}
	
}