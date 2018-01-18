package com.lolteam.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lolteam.entities.MatchEntity;
import com.lolteam.model.filter.TeamMatchFilter;
import com.lolteam.services.RiotApiService;

import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

@RestController
@RequestMapping(value = "/teamSynchronizer")
public class TeamStatusSynchronizerController {

	@Autowired
	private RiotApiService riotApiService;
	
	// TODO persit into database
	private static final List<String> summonerNames = Arrays.asList("Dremsy", "0Kordan0", "Victoriusss", "Luidji94", "Wasagreen");

	//TODO JAVADOC -> return emptyList if a error occurs 
	@RequestMapping(value = "/getTeamSummoners", method = RequestMethod.GET)
	public List<Summoner> getTeamSummoners() {
		return summonerNames.stream()
		.map(this::getSummonerByName)
		.map(Optional::get)
		.collect(Collectors.toList());
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
			return riotApiService.getMatchListByAccountId(Platform.EUW, summoner.getAccountId()).getMatches();
		} catch (RiotApiException e) {
			return null;
			//TODO HANDLE ERROR
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