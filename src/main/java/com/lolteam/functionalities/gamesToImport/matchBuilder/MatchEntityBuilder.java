package com.lolteam.functionalities.gamesToImport.matchBuilder;

import com.lolteam.entities.match.MatchEntity;

public interface MatchEntityBuilder {
	
	MatchEntityBuilder extractSimpleStatsFromParticipants();

	MatchEntity get();

}
