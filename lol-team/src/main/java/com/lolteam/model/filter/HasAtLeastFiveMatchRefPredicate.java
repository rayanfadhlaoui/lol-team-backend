package com.lolteam.model.filter;

import java.util.function.Predicate;

import net.rithms.riot.api.endpoints.match.dto.MatchReference;

class HasAtLeastFiveMatchRefPredicate implements Predicate<MatchReference>{

	public long previousValue = -1;
	public int valueCount;

	@Override
	public boolean test(MatchReference matchReference) {
		if(previousValue == matchReference.getGameId()) {
			valueCount++;
		}
		else {
			valueCount = 1;
			previousValue = matchReference.getGameId();
		}
		
		return valueCount == 5;
	}
	
}