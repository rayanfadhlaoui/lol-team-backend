package com.lolteam.framework.factory.entities;

import com.lolteam.entities.MatchEntity;

import net.rithms.riot.api.endpoints.match.dto.MatchReference;

public class MatchEntityFactory {
	public MatchEntity createMatch(MatchReference matchReference) {
		MatchEntity matchEntity = new MatchEntity();
		int gameId = Long.valueOf(matchReference.getGameId())
		                      .intValue();
		matchEntity.setId(gameId);
		return matchEntity;
	}
}
