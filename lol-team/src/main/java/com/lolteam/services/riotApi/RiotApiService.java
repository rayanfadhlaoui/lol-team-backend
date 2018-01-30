package com.lolteam.services.riotApi;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lolteam.framework.utils.properties.LolTeamProperties;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.endpoints.static_data.dto.Champion;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.api.endpoints.match.dto.Match;
import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.constant.Platform;

@Service
public class RiotApiService extends RiotApi {
	private static final String LOL_API_KEY = "key";
	private RiotApiHandle riotApiHandle = new RiotApiHandle();

	public RiotApiService() {
		super(new ApiConfig().setKey(LolTeamProperties.getPropertity(LOL_API_KEY)));
	}

	// public Optional<MatchTimeline> getTimelineByMatchId(long matchId) {
	// MatchTimeline matchTimeline = riotApiHandle.execute(() ->
	// getTimelineByMatchId(Platform.EUW, matchId));
	// return Optional.ofNullable(matchTimeline);
	// }

	public Optional<Match> getMatch(long matchId) {
		Match match = riotApiHandle.execute(() -> getMatch(Platform.EUW, matchId));
		return Optional.ofNullable(match);
	}

	public Optional<Champion> getChampion(int championId) {
		Champion champion = riotApiHandle.execute(() -> getDataChampion(Platform.EUW, championId));
		return Optional.ofNullable(champion);
	}

	public Optional<Summoner> getSummonerByAccountId(long accountId) {
		Summoner summoner = riotApiHandle.execute(() -> getSummonerByAccount(Platform.EUW, accountId));
		return Optional.ofNullable(summoner);
	}

	// TODO FINISH THE JAVADOC
	/**
	 * @return return an empty list if a error occur
	 */
	public List<MatchReference> findMatchReferenceListForSummoner(Summoner summoner) {
		List<MatchReference> matchReferences = riotApiHandle.execute(() -> getMatchListByAccountId(Platform.EUW, summoner.getAccountId()).getMatches());
		return matchReferences == null ? Collections.emptyList() : matchReferences;
	}

	public Optional<Summoner> getSummonerByName(String summonerName) {
		Summoner summoner = riotApiHandle.execute(() -> getSummonerByName(Platform.EUW, summonerName));
		return Optional.ofNullable(summoner);
	}
	
	public MatchList getMatchListByAccountId(long accountId, long beginTime, int startIndex, int endIndex) {
		return 	riotApiHandle.execute( () -> getMatchListByAccountId(Platform.EUW, accountId, null, null, null, beginTime, -1, startIndex, endIndex));

	}
}
