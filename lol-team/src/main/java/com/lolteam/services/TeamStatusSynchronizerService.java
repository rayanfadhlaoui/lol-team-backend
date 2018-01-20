package com.lolteam.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lolteam.entities.MatchEntity;
import com.lolteam.model.filter.TeamMatchFilter;
import com.lolteam.model.importer.MatchEntityImporter;

import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

@Service
public class TeamStatusSynchronizerService {

	@Autowired
	private RiotApiService riotApiService;

	// TODO JAVADOC -> return emptyList if a error occurs
	public List<Summoner> getTeamSummoners(List<String> summonerNames) {
		return summonerNames.stream()
				.map(this::getSummonerByName)
				.map(Optional::get)
				.collect(Collectors.toList());
	}

	/**
	 * Find recent games where all summoners have played
	 * 
	 * @param summoners
	 *            Summoners part of the same team.
	 * @return Recent games played by all summoners
	 */
	public List<MatchEntity> findRecentTeamMatches(List<Summoner> summoners) {
		TeamMatchFilter teamMatchFilter = new TeamMatchFilter();

		Map<Long, List<MatchReference>> matchReferencesBySummonerId = new HashMap<>();
		
		
		List<MatchReference> matchReferenceList = summoners.stream()
				.peek(summoner -> matchReferencesBySummonerId.put(summoner.getId(), findMatchReferenceListByAccountId(summoner)))
				.map(summoner -> matchReferencesBySummonerId.get(summoner.getId()))
				.flatMap(List::stream)
				.collect(Collectors.toList());

		List<Integer> communMatchesIds = teamMatchFilter.groupForTeam(matchReferenceList, 5);
		MatchEntityImporter matchEntityImporter = new MatchEntityImporter(communMatchesIds, matchReferencesBySummonerId, riotApiService);
		return matchEntityImporter.importAllMatches();
	}

	private List<MatchReference> findMatchReferenceListByAccountId(Summoner summoner) {
		try {
			return riotApiService.getMatchListByAccountId(Platform.EUW, summoner.getAccountId()).getMatches();
		} catch (RiotApiException e) {
			return null;
			// TODO HANDLE ERROR
		}
	}

	private Optional<Summoner> getSummonerByName(String summonerName) {
		try {
			return Optional.of(riotApiService.getSummonerByName(Platform.EUW, summonerName));
		} catch (RiotApiException e) {
			return Optional.empty();
		}
	}

}
