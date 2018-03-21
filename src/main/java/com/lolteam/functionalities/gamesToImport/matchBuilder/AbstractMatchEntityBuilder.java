package com.lolteam.functionalities.gamesToImport.matchBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.lolteam.entities.general.ChampionEntity;
import com.lolteam.entities.general.SummonerEntity;
import com.lolteam.entities.match.DamageEntity;
import com.lolteam.entities.match.ExperienceEntity;
import com.lolteam.entities.match.FarmEntity;
import com.lolteam.entities.match.GameMode;
import com.lolteam.entities.match.GoldEntity;
import com.lolteam.entities.match.KdaEntity;
import com.lolteam.entities.match.MatchEntity;
import com.lolteam.entities.match.ParticipantStatsEntity;
import com.lolteam.entities.match.QueueEnum;
import com.lolteam.entities.match.ScoreEntity;
import com.lolteam.entities.match.SimpleStatsEntity;
import com.lolteam.entities.match.VisionEntity;
import com.lolteam.framework.core.db.EntityCache;

import net.rithms.riot.api.endpoints.match.dto.Match;
import net.rithms.riot.api.endpoints.match.dto.Participant;
import net.rithms.riot.api.endpoints.match.dto.ParticipantStats;
import net.rithms.riot.api.endpoints.match.dto.ParticipantTimeline;

abstract class AbstractMatchEntityBuilder implements MatchEntityBuilder {

	protected Match match;
	protected EntityCache<Long, SummonerEntity> summonerEntityCache;
	protected EntityCache<Integer, ChampionEntity> championEntityCache;
	protected final List<Participant> winingParticipants = new ArrayList<>();
	protected final List<Participant> losingParticipants = new ArrayList<>();
	protected final MatchEntity matchEntity = new MatchEntity();

	AbstractMatchEntityBuilder(Match match, EntityCache<Long, SummonerEntity> summonerEntityCache,
			EntityCache<Integer, ChampionEntity> championEntityCache) {
		this.match = match;
		this.summonerEntityCache = summonerEntityCache;
		this.championEntityCache = championEntityCache;

		matchEntity.setGameId(match.getGameId());
		matchEntity.setGameDuration(match.getGameDuration());
		matchEntity.setGameType(match.getGameType());
		try {
			matchEntity.setQueue(QueueEnum.valueOf(match.getQueueId()));
		} catch (IllegalArgumentException e) {
			System.out.println("******************************************************");
			// TODO ADD LOG
			System.out.println("Add QueueEnum " + match.getQueueId() + " to 'QueueEnum' ref https://developer.riotgames.com/game-constants.html");
			matchEntity.setQueue(QueueEnum.UNKNOWN);
			System.out.println("******************************************************");
		}

		try {
			matchEntity.setGameMode(GameMode.valueOf(match.getGameMode()));
		} catch (IllegalArgumentException e) {
			// TODO ADD LOG
			System.out.println("Add gameMode " + match.getGameMode() + " to 'GameMode'");
			matchEntity.setGameMode(GameMode.UNKNOWN);
		}

		setVersion();
		match.getParticipants()
				.forEach(participant -> {
					boolean hasWin = participant.getStats()
							.isWin();
					if (hasWin) {
						winingParticipants.add(participant);
					} else {
						losingParticipants.add(participant);
					}
				});

	}

	@Override
	public MatchEntityBuilder extractSimpleStatsFromParticipants() {
		winingParticipants.forEach(p -> importSimpleParticipantStat(p, true));
		losingParticipants.forEach(p -> importSimpleParticipantStat(p, false));
		return this;
	}

	@Override
	public MatchEntity get() {
		return matchEntity;
	}

	abstract void setVersion();

	protected abstract GoldEntity extractGoldStats(Participant participant);

	protected abstract FarmEntity extractFarm(Participant participant);

	protected abstract void extracOtherDataFromTimeLine(ParticipantTimeline timeline, SimpleStatsEntity simpleStatsEntity);

	protected abstract ExperienceEntity extractExperience(Participant participant);

	protected abstract DamageEntity extractDamage(Participant participant);

	private SimpleStatsEntity extractSimpleStatsEntity(Participant participant) {
		ChampionEntity championEntity = championEntityCache.get(participant.getChampionId());
		SimpleStatsEntity simpleStatsEntity = new SimpleStatsEntity();

		simpleStatsEntity.setChampionEntity(championEntity);

		ParticipantStats stats = participant.getStats();
		if (stats != null) {

			KdaEntity kda = new KdaEntity();
			kda.setKills(stats.getKills());
			kda.setDeaths(stats.getDeaths());
			kda.setAssists(stats.getAssists());
			simpleStatsEntity.setKda(kda);

			VisionEntity vision = new VisionEntity();
			vision.setWardsPlaced(stats.getWardsPlaced());
			vision.setWardsKilled(stats.getWardsKilled());
			vision.setSightWardsBought(stats.getSightWardsBoughtInGame());
			vision.setVisionScore(stats.getVisionScore());

			// todo extract xp, damage

			simpleStatsEntity.setScore(extractScore(stats).orElse(null));
			simpleStatsEntity.setVision(vision);
			simpleStatsEntity.setDamage(extractDamage(participant));
			simpleStatsEntity.setExperience(extractExperience(participant));
			simpleStatsEntity.setGold(extractGoldStats(participant));
			simpleStatsEntity.setFarm(extractFarm(participant));
		} else {
			System.err.println("stats is null and version " + match.getGameVersion());
		}

		extracOtherDataFromTimeLine(participant.getTimeline(), simpleStatsEntity);
		return simpleStatsEntity;
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
		if (summonerEntity == null) {
			summonerEntity = new SummonerEntity();
			summonerEntity.setAccountId(accountId);
			if (accountId == 0) {
				summonerEntity.setName("BOT");
			} else {
				summonerEntity.setName("UNKNOWN " + accountId);
			}
			summonerEntityCache.addAndSave(summonerEntity);
		}

		SimpleStatsEntity simpleStatsEntity = extractSimpleStatsEntity(participant);

		ParticipantStatsEntity participantStatsEntity = new ParticipantStatsEntity();
		participantStatsEntity.setSummonerEntity(summonerEntity);
		participantStatsEntity.setSimpleStatsEntity(simpleStatsEntity);
		if (winner) {
			matchEntity.addWinner(participantStatsEntity);
		} else {
			matchEntity.addloser(participantStatsEntity);
		}
	}

	private Optional<ScoreEntity> extractScore(ParticipantStats stats) {
		ScoreEntity score = new ScoreEntity();
		int combatPlayerScore = stats.getCombatPlayerScore();
		int objectivePlayerScore = stats.getObjectivePlayerScore();
		int totalPlayerScore = stats.getTotalPlayerScore();
		int totalScoreRank = stats.getTotalScoreRank();

		score.setCombatPlayer(combatPlayerScore);
		score.setObjectivePlayer(objectivePlayerScore);
		score.setTotalPlayer(totalPlayerScore);
		score.setTotalRank(totalScoreRank);

		if (Stream.of(combatPlayerScore, objectivePlayerScore, totalPlayerScore, totalScoreRank)
				.anyMatch(it -> it != 0)) {
			return Optional.of(score);
		}

		return Optional.empty();
	}

}
