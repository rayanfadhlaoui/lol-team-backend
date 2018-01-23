package com.lolteam.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lolteam.entities.MatchEntity;
import com.lolteam.services.TeamStatusSynchronizerService;

import net.rithms.riot.api.endpoints.summoner.dto.Summoner;

@RestController
@RequestMapping(value = "/teamSynchronizer")
public class TeamStatusSynchronizerController {

	@Autowired
	private TeamStatusSynchronizerService teamStatusSynchronizerService;

	// TODO persit into database
	private static final List<String> summonerNames = Arrays.asList("Dremsy", "0Kordan0", "Victoriusss", "Luidji94", "Wasagreen");


	// TODO JAVADOC -> return emptyList if a error occurs
	@RequestMapping(value = "/getTeamSummoners", method = RequestMethod.GET)
	public List<Summoner> getTeamSummoners() {
		return teamStatusSynchronizerService.getTeamSummoners(summonerNames);
	}
	
	@RequestMapping(value = "/importRecentMatches", method = RequestMethod.GET)
	public List<MatchEntity> findRecentTeamMatches() {
		List<Summoner> teamSummoners = teamStatusSynchronizerService.getTeamSummoners(summonerNames);
		List<MatchEntity> recentTeamMatch = teamStatusSynchronizerService.findRecentTeamMatches(teamSummoners);
		teamStatusSynchronizerService.saveAllMatchesEntity(recentTeamMatch);
		return recentTeamMatch;
	}
}