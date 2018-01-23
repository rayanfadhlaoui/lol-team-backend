package com.lolteam.framework.factory.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lolteam.entities.ChampionEntity;
import com.lolteam.entities.MatchEntity;
import com.lolteam.entities.ParticipantStatsEntity;
import com.lolteam.entities.SimpleStatsEntity;
import com.lolteam.entities.SummonerEntity;
import com.lolteam.entities.match.GameMode;
import com.lolteam.framework.core.db.EntityCache;

import net.rithms.riot.api.endpoints.match.dto.Match;
import net.rithms.riot.api.endpoints.match.dto.Participant;

public class MatchEntityBuilder {

	private final List<Participant> winingParticipants = new ArrayList<>();
	private final List<Participant> losingParticipants = new ArrayList<>();
	private Match match;
	private final MatchEntity matchEntity = new MatchEntity();
	private EntityCache<Long, SummonerEntity> summonerEntityCache;
	private EntityCache<Integer, ChampionEntity> championEntityCache;

	public static MatchEntityBuilder init(	Match match, EntityCache<Long, SummonerEntity> summonerEntityCache,
											EntityCache<Integer, ChampionEntity> championEntityCache) {
		return new MatchEntityBuilder(match, summonerEntityCache, championEntityCache);
	}

	private MatchEntityBuilder(Match match, EntityCache<Long, SummonerEntity> summonerEntityCache,
			EntityCache<Integer, ChampionEntity> championEntityCache) {
		this.match = match;
		this.summonerEntityCache = summonerEntityCache;
		this.championEntityCache = championEntityCache;
		matchEntity.setGameId(match.getGameId());
		matchEntity.setGameDuration(match.getGameDuration());
		matchEntity.setGameMode(GameMode.valueOf(match.getGameMode()));
		matchEntity.setGameVersion(match.getGameVersion());
		match.getParticipants().forEach(participant -> {
			if (participant.getStats().isWin()) {
				winingParticipants.add(participant);
			} else {
				losingParticipants.add(participant);
			}
		});
	}

	public MatchEntityBuilder extractSimpleStatsFromParticipants() {
		winingParticipants.forEach(p -> importSimpleParticipantStat(p, true));
		losingParticipants.forEach(p -> importSimpleParticipantStat(p, false));
		return this;
	}

	public MatchEntity get() {
		return matchEntity;
	}

	private void importSimpleParticipantStat(Participant participant, boolean winner) {

		long accountId = match.getParticipantIdentities()
				.stream()
				.filter(p -> p.getParticipantId() == participant.getParticipantId())
				.findFirst()
				.get()
				.getPlayer()
				.getAccountId();

		SummonerEntity summonerEntity = summonerEntityCache.get(accountId);
		SimpleStatsEntity simpleStatsEntity = createSimpleStatsEntity(participant);

		ParticipantStatsEntity participantStatsEntity = new ParticipantStatsEntity();
		participantStatsEntity.setSummonerEntity(summonerEntity);
		participantStatsEntity.setSimpleStatsEntity(simpleStatsEntity);
		if(winner) {
			matchEntity.addWinner(participantStatsEntity);
		}
		else {
			matchEntity.addloser(participantStatsEntity);
		}
	}

	private SimpleStatsEntity createSimpleStatsEntity(Participant participant) {
		SimpleStatsEntity simpleStatsEntity = new SimpleStatsEntity();
		ChampionEntity championEntity = championEntityCache.get(participant.getChampionId());
		simpleStatsEntity.setChampionEntity(championEntity);
		simpleStatsEntity.setLane(participant.getTimeline().getLane());
		simpleStatsEntity.setRole(participant.getTimeline().getRole());
		
		Map<String, Double> creepsPerMinDeltas = participant.getTimeline().getCreepsPerMinDeltas();
		Double farmAt10 = creepsPerMinDeltas.get("0-10");
		Double farmAt10To20 = creepsPerMinDeltas.get("10-20");
		Double farmAt20To30 = creepsPerMinDeltas.get("20-30");
		
		simpleStatsEntity.setFarmAt10(farmAt10);
		simpleStatsEntity.setFarmAt10To20(farmAt10To20);
		simpleStatsEntity.setFarmAt20To30(farmAt20To30);
		return simpleStatsEntity;
	}

}
