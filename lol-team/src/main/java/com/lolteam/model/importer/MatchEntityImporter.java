package com.lolteam.model.importer;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.lolteam.entities.MatchEntity;
import com.lolteam.framework.factory.entities.MatchEntityFactory;
import com.lolteam.services.RiotApiService;

import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.match.dto.MatchTimeline;

public class MatchEntityImporter {

	private List<Integer> matchesIdsToImport;
	private Map<Long, List<MatchReference>> matchReferencesBySummonerId;
	private RiotApiService riotApiService;
	private MatchEntityFactory matchEntityFactory;

	public MatchEntityImporter(List<Integer> matchesIdsToImport, Map<Long, List<MatchReference>> matchReferencesBySummonerId,
			RiotApiService riotApiService) {
		this.matchesIdsToImport = matchesIdsToImport;
		this.matchReferencesBySummonerId = matchReferencesBySummonerId;
		this.riotApiService = riotApiService;
		matchEntityFactory = new MatchEntityFactory();
	}

	//TODO JAVADOC + COMPLETER LA FACTORY + AJOUTER LE DETAIL PAR SUMMONERS
	public List<MatchEntity> importAllMatches() {
		
		return matchesIdsToImport.stream()
				.map(this::createMatchEntity)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	private MatchEntity createMatchEntity(Integer matchId) {
		Optional<MatchTimeline> optionalMatchTimeline = riotApiService.getTimelineByMatchId(matchId);
		if(optionalMatchTimeline.isPresent()) {
			return matchEntityFactory.createMatch(optionalMatchTimeline.get(), matchId);
		}
		
		return null;
	}

}
