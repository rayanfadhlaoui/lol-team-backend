package com.lolteam.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lolteam.entities.MatchEntity;
import com.lolteam.entities.SummonerEntity;
import com.lolteam.entities.TeamEntity;
import com.lolteam.model.importer.MatchEntityImporter;
import com.lolteam.model.importer.SummonerGamesImporter;
import com.lolteam.model.json.myteam.AddSummonerBody;
import com.lolteam.services.ChampionService;
import com.lolteam.services.MatchEntityService;
import com.lolteam.services.SummonerService;
import com.lolteam.services.TeamService;
import com.lolteam.services.riotApi.RiotApiService;

@RestController
@RequestMapping(value = "/myTeam")
public class MyTeamControler {

	@Autowired
	private TeamService teamService;

	@Autowired
	private SummonerService summonerService;

	@Autowired
	private MatchEntityService matchEntityService;

	@Autowired
	private RiotApiService riotApiService;

	@Autowired
	private ChampionService championService;

	private MatchEntityImporter matchEntityImporter;
	
	@PostConstruct
	public void init() {
		matchEntityImporter = new MatchEntityImporter(riotApiService, summonerService ,championService);
	}
	

	@RequestMapping(value = "/findTeam", method = RequestMethod.POST)
	public TeamEntity create(@RequestBody Long userId) {
		return teamService.findTeamByUserId(userId).orElse(null);
	}

	@Transactional
	@RequestMapping(value = "/addSummonerToTeam", method = RequestMethod.POST)
	public TeamEntity addSummonerToTeam(@RequestBody AddSummonerBody addSummonerRequest) {
		Optional<SummonerEntity> optionalSummoner = summonerService.smartLoadSummoner(addSummonerRequest.getSummonerName());
		if (!optionalSummoner.isPresent()) {
			return null;
		}

		addSummonerRequest.getTeam().addSummoner(optionalSummoner.get());
		teamService.save(addSummonerRequest.getTeam());
		return addSummonerRequest.getTeam();
	}

	/**
	 * Import recent games of a summoner based on its last update.
	 * 
	 * @param summonerEntity
	 *            The summoner.
	 * @return The summoner with the current date as its last update.
	 */
	@RequestMapping(value = "/importGames", method = RequestMethod.POST)
	public SummonerEntity importGames(@RequestBody SummonerEntity summonerEntity) {
		SummonerGamesImporter summonerGamesImporter = new SummonerGamesImporter(riotApiService, matchEntityImporter);
		List<MatchEntity> matches = summonerGamesImporter.importRecentGames(summonerEntity);
		matchEntityService.saveAll(matches);
		summonerEntity.setLastUpdate(LocalDate.now());
		summonerService.save(summonerEntity);
		return summonerEntity;
	}

}
