package com.lolteam.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lolteam.entities.MatchEntity;
import com.lolteam.model.filter.TeamMatchFilter;
import com.lolteam.model.importer.MatchEntityImporter;
import com.lolteam.services.riotApi.RiotApiService;

import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;

@Service
public class TeamStatusSynchronizerService {

	@Autowired
	private RiotApiService riotApiService;
	
	@Autowired
	private MatchEntityService matchEntityService;
	
	@Autowired
	private ChampionService championService;
	
	@Autowired
	private SummonerService summonerService;

	// TODO JAVADOC -> return emptyList if a error occurs
	public List<Summoner> getTeamSummoners(List<String> summonerNames) {
		return summonerNames.stream()
				.map(riotApiService::getSummonerByName)
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
				.peek(summoner -> matchReferencesBySummonerId.put(summoner.getId(), riotApiService.findMatchReferenceListForSummoner(summoner)))
				.map(summoner -> matchReferencesBySummonerId.get(summoner.getId()))
				.flatMap(List::stream)
				.collect(Collectors.toList());

		List<Long> communMatchesIds = teamMatchFilter.groupForTeam(matchReferenceList, 5);
		MatchEntityImporter matchEntityImporter = new MatchEntityImporter(riotApiService, summonerService,championService);
		return matchEntityImporter.importAllMatches(communMatchesIds);
	}

	@Transactional
	public void saveAllMatchesEntity(List<MatchEntity> matchEntities) {
		matchEntityService.saveAll(matchEntities);
	}

}
