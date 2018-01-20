package com.lolteam.services;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lolteam.framework.utils.properties.LolTeamProperties;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.match.dto.MatchTimeline;
import net.rithms.riot.constant.Platform;

@Service
public class RiotApiService extends RiotApi {
	private static final String LOL_AOI_KEY = "key";

	public RiotApiService() {
		super(new ApiConfig().setKey(LolTeamProperties.getPropertity(LOL_AOI_KEY)));
	}

	public Optional<MatchTimeline> getTimelineByMatchId(long matchId) {
		try {
			return Optional.of(getTimelineByMatchId(Platform.EUW, matchId));
		} catch (RiotApiException e) {
			handleRiotApiException(e);
		}
		return Optional.empty();
	}

	private void handleRiotApiException(RiotApiException e) {
		e.printStackTrace();
	}
}
