package com.lolteam.functionalities.gamesToImport.matchBuilder;

import com.lolteam.entities.ChampionEntity;
import com.lolteam.entities.SummonerEntity;
import com.lolteam.framework.core.db.EntityCache;

import net.rithms.riot.api.endpoints.match.dto.Match;

public class MatchEntityBuilderFactory {

	private EntityCache<Long, SummonerEntity> summonerEntityCache;
	private EntityCache<Integer, ChampionEntity> championEntityCache;

	public MatchEntityBuilderFactory(EntityCache<Long, SummonerEntity> summonerEntityCache,
			EntityCache<Integer, ChampionEntity> championEntityCache) {
		this.summonerEntityCache = summonerEntityCache;
		this.championEntityCache = championEntityCache;
	}

	public MatchEntityBuilder create(Match match) {
		if (match.getGameVersion() == null) {
			return new NoVersionMatchEntityBuilder(match, summonerEntityCache, championEntityCache);
		}
		return new MatchEntityBuilderImpl(match, summonerEntityCache, championEntityCache);
	}

}
