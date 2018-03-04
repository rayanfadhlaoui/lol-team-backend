package com.lolteam.functionalities.gamesToImport.matchImporter;

import java.util.Optional;

import com.lolteam.entities.ChampionEntity;
import com.lolteam.entities.SummonerEntity;
import com.lolteam.entities.match.MatchEntity;
import com.lolteam.entities.treatment.GamesToImportEntity;
import com.lolteam.entities.treatment.ImportStatus;
import com.lolteam.framework.core.db.DataNotFullException;
import com.lolteam.framework.core.db.EntityCache;
import com.lolteam.functionalities.gamesToImport.matchBuilder.MatchEntityBuilderFactory;
import com.lolteam.services.ChampionService;
import com.lolteam.services.MatchEntityService;
import com.lolteam.services.SummonerService;
import com.lolteam.services.riotApi.RiotApiService;
import com.lolteam.utils.exceptions.RiotApiHandleException;

import net.rithms.riot.api.endpoints.match.dto.Match;

public class MatchEntityImporter {

	private final RiotApiService riotApiService;
	private MatchEntityBuilderFactory matchEntityBuilderFactory;

	public MatchEntityImporter(RiotApiService riotApiService, SummonerService summonerService, ChampionService championService,
			MatchEntityService matchEntityService) {
		this.riotApiService = riotApiService;
		initCache(summonerService, championService);
	}

	private void initCache(SummonerService summonerService, ChampionService championService) {
		EntityCache<Long, SummonerEntity> summonerCache = summonerService.getCache();
		EntityCache<Integer, ChampionEntity> championCache = championService.getCache();

		matchEntityBuilderFactory = new MatchEntityBuilderFactory(summonerCache, championCache);
	}

	public MatchEntity importMatchEntity(GamesToImportEntity gamesToImportEntity) {

		gamesToImportEntity.setImportStatus(ImportStatus.SUCCESS);
		long matchId = gamesToImportEntity.getMatchId();
		Optional<Match> optionalMatch = Optional.empty();
		try {
			optionalMatch = riotApiService.getMatch(matchId);
		} catch (RiotApiHandleException e) {
			System.err.println(e.getMessage() + " MatchId = " + matchId);
		}
		if (!optionalMatch.isPresent()) {
			System.out.println("for some reason match is not present");
			gamesToImportEntity.setImportStatus(ImportStatus.ERROR);
			return null;
		}

		try {
			return matchEntityBuilderFactory.create(optionalMatch.get())
											.extractSimpleStatsFromParticipants()
											.get();
		} catch (DataNotFullException e) {
			gamesToImportEntity.setImportStatus(ImportStatus.ERROR);
			return null;
		}
	}
}
