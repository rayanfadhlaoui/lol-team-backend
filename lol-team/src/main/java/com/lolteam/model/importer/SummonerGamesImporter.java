package com.lolteam.model.importer;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import com.lolteam.entities.MatchEntity;
import com.lolteam.entities.SummonerEntity;
import com.lolteam.services.riotApi.RiotApiService;

import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;

public class SummonerGamesImporter {

	private final MatchEntityImporter matchEntityImporter;
	private final RiotApiService riotApiService;

	public SummonerGamesImporter(RiotApiService riotApiService, MatchEntityImporter matchEntityImporter) {
		this.riotApiService = riotApiService;
		this.matchEntityImporter = matchEntityImporter;
	}

	public List<MatchEntity> importRecentGames(SummonerEntity summonerEntity) {
		List<Long> matchIdsToImport = findGamesIdToImport(summonerEntity);
		return matchEntityImporter.importAllMatches(matchIdsToImport);
	}

	private List<Long> findGamesIdToImport(SummonerEntity summonerEntity) {
		Builder<List<MatchReference>> streamBuilder = Stream.builder(); 
		LocalDate lastUpdate = summonerEntity.getLastUpdate();
		int startIndex = 0;
		int endIndex = 100;
		long beginTime = lastUpdate != null ? lastUpdate.toEpochDay() :-1;
		
		while(startIndex != endIndex) {
			MatchList matchList = riotApiService.getMatchListByAccountId(summonerEntity.getAccountId(), beginTime, startIndex, endIndex);
			streamBuilder.accept(matchList.getMatches());
			startIndex = matchList.getStartIndex() + 100;
			endIndex = matchList.getEndIndex() + 100;
		}

		return streamBuilder.build()
				.flatMap(List::stream)
				.map(MatchReference::getGameId)
				.collect(Collectors.toList());
	}

}
