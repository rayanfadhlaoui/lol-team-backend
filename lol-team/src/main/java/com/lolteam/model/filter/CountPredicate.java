package com.lolteam.model.filter;

import java.util.function.Predicate;

class CountPredicate implements Predicate<Integer>{

	public Integer previousValue = -1;
	public int valueCount;
	private int nbCountExpected;

	public CountPredicate(int nbCountExpected) {
		this.nbCountExpected = nbCountExpected;
	}

	@Override
	public boolean test(Integer value) {
		if(previousValue == value) {
			valueCount++;
		}
		else {
			valueCount = 1;
			previousValue = value;
		}
		
		return valueCount == nbCountExpected;
	}
	
}