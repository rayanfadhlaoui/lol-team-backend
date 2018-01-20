package com.lolteam.framework.factory.entities;

import com.lolteam.entities.MatchEntity;

import net.rithms.riot.api.endpoints.match.dto.MatchTimeline;

public class MatchEntityFactory {

	public MatchEntity createMatch(MatchTimeline matchTimeline, Integer matchId) {
		MatchEntity matchEntity = new MatchEntity();
		matchEntity.setId(matchId);
		return matchEntity;
	}
}
