package com.lolteam.controllers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lolteam.entities.MatchEntity;
import com.lolteam.model.filter.TeamMatchFilter;
import com.lolteam.riot.api.RiotApiBuilder;

import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

@RestController
@RequestMapping(value = "/TeamSynchronizer")
public class TeamStatusSynchronizerController {

	// TODO persit into database
	private static final List<String> summonerNames = Arrays.asList("Dremsy", "0Kordan0", "Victoriusss", "Luidji94", "Wasagreen");
	private final RiotApi api;

	public TeamStatusSynchronizerController() {
		this.api = RiotApiBuilder.get();
	}


	//TODO JAVADOC -> return emptyList if a error occurs 
	@RequestMapping(value = "/getTeamSummoners", method = RequestMethod.GET)
	public List<Summoner> getTeamSummoners() {
		Stream<Optional<Summoner>> summonerStream = summonerNames.stream()
		.map(this::getSummonerByName);
		if(summonerStream.allMatch(Optional::isPresent)) {
			return summonerStream.map(Optional::get)
			.collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}

	private Optional<Summoner> getSummonerByName(String summonerName) {
		try {
			return Optional.of(api.getSummonerByName(Platform.EUW, summonerName));
		} catch (RiotApiException e) {
			return Optional.empty();
		}
	}
	
	@RequestMapping(value = "/importRecentMatches", method = RequestMethod.GET)
	public List<MatchEntity> findRecentTeamMatches() {
		TeamMatchFilter teamMatchFilter = new TeamMatchFilter();
		List<Summoner> summoners = getTeamSummoners();
		List<MatchReference> matchReferenceList = summoners.stream()
		.map(this::findMatchReferenceListByAccountId)
		.flatMap(List::stream)
		.collect(Collectors.toList());
		
		return teamMatchFilter.groupForTeam(matchReferenceList);
		
	}


	private List<MatchReference> findMatchReferenceListByAccountId(Summoner summoner) {
		try {
			MatchList matchListByAccountId = api.getMatchListByAccountId(Platform.EUW, summoner.getAccountId());
			return matchListByAccountId.getMatches();
		} catch (RiotApiException e) {
			return null;
			//TODO HANDLE ERROR
		}
	}
	
}