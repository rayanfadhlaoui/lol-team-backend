package com.lolteam.services.riotApi;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.lolteam.entities.match.QueueEnum;
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
public class RiotApiService {
	private static final String LOL_API_KEY = "key";
	private RiotApiHandle riotApiHandle = new RiotApiHandle();
	private RiotApi riotApi;

	public RiotApiService() {
		riotApi = new RiotApi(new ApiConfig().setKey(LolTeamProperties.getPropertity(LOL_API_KEY)));
	}

	// public Optional<MatchTimeline> getTimelineByMatchId(long matchId) {
	// MatchTimeline matchTimeline = riotApiHandle.execute(() ->
	// getTimelineByMatchId(Platform.EUW, matchId));
	// return Optional.ofNullable(matchTimeline);
	// }

	public Optional<Match> getMatch(long matchId) {
		return riotApiHandle.execute(() -> riotApi.getMatch(Platform.EUW, matchId));
	}

	public Optional<Champion> getChampion(int championId) {
		return riotApiHandle.execute(() -> riotApi.getDataChampion(Platform.EUW, championId));
	}

	public Optional<Summoner> getSummonerByAccountId(long accountId) {
		return riotApiHandle.execute(() -> riotApi.getSummonerByAccount(Platform.EUW, accountId));
	}

	// TODO FINISH THE JAVADOC
	/**
	 * @return return an empty list if a error occur
	 */
	public List<MatchReference> findMatchReferenceListForSummoner(Summoner summoner) {
		return riotApiHandle.execute(() -> riotApi.getMatchListByAccountId(Platform.EUW, summoner.getAccountId())
				.getMatches())
				.orElse(Collections.emptyList());
	}

	public Optional<Summoner> getSummonerByName(String summonerName) {
		return riotApiHandle.execute(() -> riotApi.getSummonerByName(Platform.EUW, summonerName));
	}

	public MatchList getMatchListByAccountId(long accountId, long beginTime, int startIndex, int endIndex) {
		Set<Integer> queues = getQueuesToImport();
		return riotApiHandle
				.execute(() -> riotApi.getMatchListByAccountId(Platform.EUW, accountId, null, queues, null, beginTime, -1, startIndex, endIndex))
				.orElse(new MatchList());
	}

	private Set<Integer> getQueuesToImport() {
		QueueEnum[] rankedTeam = { QueueEnum.RANKED_FLEX_5V5, QueueEnum.RANKED_DYNAMIC_5V5, QueueEnum.RANKED_PREMADE_5V5 };
		QueueEnum[] rankedSolo = { QueueEnum.RANKED_SOLO_5V5, QueueEnum.RANKED_SOLO_5V5_V2 };
		QueueEnum[] normal = { QueueEnum.DRAFT_PICK_5V5, QueueEnum.DRAFT_PICK_5V5_V2, QueueEnum.BLIND_PICK_5V5_V2, QueueEnum.BLIND_PICK_5V5 };
		return Stream.of(rankedTeam, rankedSolo, normal)
				.flatMap(Arrays::stream)
				.map(QueueEnum::getValue)
				.collect(Collectors.toSet());
	}
}
