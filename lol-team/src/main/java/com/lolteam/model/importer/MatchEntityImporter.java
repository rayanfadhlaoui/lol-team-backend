package com.lolteam.model.importer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.lolteam.entities.ChampionEntity;
import com.lolteam.entities.MatchEntity;
import com.lolteam.entities.SummonerEntity;
import com.lolteam.framework.core.db.EntityCache;
import com.lolteam.framework.factory.entities.MatchEntityBuilder;
import com.lolteam.services.ChampionService;
import com.lolteam.services.SummonerService;
import com.lolteam.services.riotApi.RiotApiService;

import net.rithms.riot.api.endpoints.match.dto.Match;

public class MatchEntityImporter {

	private RiotApiService riotApiService;
	private EntityCache<Long, SummonerEntity> summonerCache;
	private EntityCache<Integer, ChampionEntity> championCache;

	public MatchEntityImporter(RiotApiService riotApiService, SummonerService summonerService, ChampionService championService) {
		this.riotApiService = riotApiService;
		initCache(summonerService, championService);
	}

	private void initCache(SummonerService summonerService, ChampionService championService) {
		summonerCache = new EntityCache<>(accountId -> summonerService.smartLoadSummoner(accountId).get(), (accountId, summoner) -> summoner.getAccountId() == accountId);

		championCache = new EntityCache<>(championId -> championService.smartLoadChampion(championId).get(),
				(championId, champion) -> champion.getChampionId() == championId.intValue());

	}

	// TODO JAVADOC + COMPLETER LA FACTORY + AJOUTER LE DETAIL PAR SUMMONERS
	public List<MatchEntity> importAllMatches(List<Long> matchesIdsToImport) {

		return matchesIdsToImport.stream()
				.map(this::createMatchEntity)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	private MatchEntity createMatchEntity(Long matchId) {
		Match match = riotApiService.getMatch(matchId).orElse(null);
		return MatchEntityBuilder.init(match, summonerCache, championCache)
				.extractSimpleStatsFromParticipants()
				.get();
	}
}
