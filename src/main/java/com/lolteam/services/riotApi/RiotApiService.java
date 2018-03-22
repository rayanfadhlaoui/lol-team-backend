package com.lolteam.services.riotApi;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lolteam.entities.match.QueueEnum;
import com.lolteam.services.SettingService;

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
	private RiotApiHandle riotApiHandle = new RiotApiHandle();
	private RiotApi riotApi;
	
	@Autowired
	private SettingService settingService;

	@PostConstruct
	public void init() {
		riotApi = new RiotApi(new ApiConfig().setKey(settingService.getRiotKey()));
	}

	// public Optional<MatchTimeline> getTimelineByMatchId(long matchId) {
	// MatchTimeline matchTimeline = riotApiHandle.execute(() ->
	// getTimelineByMatchId(Platform.EUW, matchId));
	// return Optional.ofNullable(matchTimeline);
	// }

	/** Use the riot API to returns an optional match associated to parameter matchId.<br/>
	 * <b>warning</b> this method might take a long time if the riot API has reached 
	 * its maximum request
	 *  @param matchId 
	 * 	 	The match id
	 *  @return An optional on {@link Match} 
	 */ 
	public Optional<Match> getMatch(long matchId) {
		return riotApiHandle.execute(() -> riotApi.getMatch(Platform.EUW, matchId));
	}

	/**
	 * Returns an optional champion associated to parameter championId
	 * <b>warning</b> this method might take a long time if the riot API has reached 
	 * its maximum request
	 * @param championId 
	 * 		The champion id
	 * @return An optional on {@link Champion} 
	 */ 
	public Optional<Champion> getChampion(int championId) {
		return riotApiHandle.execute(() -> riotApi.getDataChampion(Platform.EUW, championId));
	}

	/**
	 * Returns an option on a summoner based on its account id.
	  * <b>warning</b> this method might take a long time if the riot API has reached 
	 * its maximum request
	 * 
	 * @param accountId 
	 * 		The account id.
	 * 
	 * @return an optional on a summoner.
	 */
	public Optional<Summoner> getSummonerByAccountId(long accountId) {
		return riotApiHandle.execute(() -> riotApi.getSummonerByAccount(Platform.EUW, accountId));
	}
	
	/**
	 * Returns an option on a summoner based on its name.
	  * <b>warning</b> this method might take a long time if the riot API has reached 
	 * its maximum request
	 * 
	 * @param summoner name 
	 * 		The summoner name.
	 * 
	 * @return an optional on a summoner.
	 */
	public Optional<Summoner> getSummonerByName(String summonerName) {
		return riotApiHandle.execute(() -> riotApi.getSummonerByName(Platform.EUW, summonerName));
	}

	/**
	 * Returns a list of match reference based on a user.
	 * If no match were found, an empty list will be returned.
	 * <b>warning</b> this method might take a long time if the riot API has reached 
	 * its maximum request
	 * 
	 * @param summoner 
	 * 		The summoner.
	 * 
	 * @return a list of match reference
	 */
	public List<MatchReference> findMatchReferenceListForSummoner(Summoner summoner) {
		return riotApiHandle.execute(() -> riotApi.getMatchListByAccountId(Platform.EUW, summoner.getAccountId())
				.getMatches())
				.orElse(Collections.emptyList());
	}

	/**
	 * Returns a {@link MatchList} based on many parameters.
	 * <b>A maximum of 100 results are returned, so if you want to navigate through 
	 * more than that, use the indexes.</b>
	 * If no match were found, an empty {@link MatchList} will be returned.
	 * <b>warning</b> this method might take a long time if the riot API has reached 
	 * its maximum request
	 * 
	 * @param accountId 
	 * 		The account id.
	 * @param beginTime 
	 * 		The begin date is used to return all games played after it.
	 * @param startIndex 
	 * 		The start index
	 * @param endIndex 
	 * 		The end index.
	 * 
	 * @return an object that contains all games played by a user.
	 */
	public MatchList getMatchListByAccountId(long accountId, long beginTime, int startIndex, int endIndex) {
		Set<Integer> queues = getQueuesToImport();
		return riotApiHandle
				.execute(() -> riotApi.getMatchListByAccountId(Platform.EUW, accountId, null, queues, null, beginTime, -1, startIndex, endIndex))
				.orElse(new MatchList());
	}

	/**
	 * Returns a set of all the queues that needs to be imported.
	 * 
	 * @return A set of integers representing the queues to import.
	 */
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
