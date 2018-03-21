package com.lolteam.functionalities.gamesToImport.matchBuilder;

import java.util.Map;

import com.lolteam.entities.general.ChampionEntity;
import com.lolteam.entities.general.SummonerEntity;
import com.lolteam.entities.match.DamageEntity;
import com.lolteam.entities.match.ExperienceEntity;
import com.lolteam.entities.match.FarmEntity;
import com.lolteam.entities.match.GoldEntity;
import com.lolteam.entities.match.SimpleStatsEntity;
import com.lolteam.framework.core.db.EntityCache;

import net.rithms.riot.api.endpoints.match.dto.Match;
import net.rithms.riot.api.endpoints.match.dto.Participant;
import net.rithms.riot.api.endpoints.match.dto.ParticipantStats;
import net.rithms.riot.api.endpoints.match.dto.ParticipantTimeline;

class MatchEntityBuilderImpl extends AbstractMatchEntityBuilder {

	private static final String KEY_20_30 = "20-30";
	private static final String KEY_10_20 = "10-20";
	private static final String KEY_0_10 = "0-10";

	MatchEntityBuilderImpl(Match match, EntityCache<Long, SummonerEntity> summonerEntityCache,
			EntityCache<Integer, ChampionEntity> championEntityCache) {
		super(match, summonerEntityCache, championEntityCache);
	}

	@Override
	protected void extracOtherDataFromTimeLine(ParticipantTimeline timeline, SimpleStatsEntity simpleStatsEntity) {
		simpleStatsEntity.setLane(timeline.getLane());
		simpleStatsEntity.setRole(timeline.getRole());
	}

	@Override
	void setVersion() {
		matchEntity.setGameVersion(match.getGameVersion());
	}

	@Override
	protected FarmEntity extractFarm(Participant participant) {
		FarmEntity farmEntity = new FarmEntity();

		Map<String, Double> creepsPerMinDeltas = participant.getTimeline()
				.getCreepsPerMinDeltas();
		if (creepsPerMinDeltas != null) {
			Double farmAt10 = creepsPerMinDeltas.get(KEY_0_10);
			Double farmAt10To20 = creepsPerMinDeltas.get(KEY_10_20);
			Double farmAt20To30 = creepsPerMinDeltas.get(KEY_20_30);

			farmEntity.setFarmAt10(farmAt10);
			farmEntity.setFarmAt10To20(farmAt10To20);
			farmEntity.setFarmAt20To30(farmAt20To30);
		}
		double farmAtEnd = participant.getStats()
				.getTotalMinionsKilled() / match.getGameDuration();
		farmEntity.setFarmAtEnd(farmAtEnd);
		return farmEntity;
	}

	@Override
	protected GoldEntity extractGoldStats(Participant participant) {
		ParticipantStats stats = participant.getStats();
		GoldEntity gold = new GoldEntity();
		gold.setGoldAtEnd(stats.getGoldEarned());
		gold.setGoldSpent(stats.getGoldSpent());
		Map<String, Double> goldPerMinDeltas = participant.getTimeline().getGoldPerMinDeltas();
		if(goldPerMinDeltas != null) {
			Double goldAt10 = goldPerMinDeltas.get(KEY_0_10);
			Double goldAt10To20 = goldPerMinDeltas.get(KEY_10_20);
			Double goldAt20To30 = goldPerMinDeltas.get(KEY_20_30);

			gold.setGoldAt10(goldAt10);
			gold.setGoldAt10To20(goldAt10To20);
			gold.setGoldAt20To30(goldAt20To30);
		}
		return gold;
	}

	protected ExperienceEntity extractExperience(Participant participant) {
		ExperienceEntity experience = new ExperienceEntity();
		
		Map<String, Double> xpDiffMinDeltas = participant.getTimeline().getXpDiffPerMinDeltas();
		if(xpDiffMinDeltas != null) {
			Double xpAt10 = xpDiffMinDeltas.get(KEY_0_10);
			Double xpAt10To20 = xpDiffMinDeltas.get(KEY_10_20);
			Double xpAt20To30 = xpDiffMinDeltas.get(KEY_20_30);

			experience.setExperienceAt10(xpAt10);
			experience.setExperienceAt10To20(xpAt10To20);
			experience.setExperienceAt20To30(xpAt20To30);
		}
		
		experience.setLevelAtEnd(participant.getStats().getChampLevel());
		return experience;
	}
	
	@Override
	protected DamageEntity extractDamage(Participant participant) {
		DamageEntity damage = new DamageEntity();
		ParticipantStats stats = participant.getStats();
		long damageDealtToObjectives = stats.getDamageDealtToObjectives();
		long damageDealtToTurrets = stats.getDamageDealtToTurrets();
		long damageSelfMitigated = stats.getDamageSelfMitigated();
		long totalDamageDealt = stats.getTotalDamageDealtToChampions();
		long totalDamageTaken = stats.getTotalDamageTaken();
		
		damage.setDamageDealtToObjectives(damageDealtToObjectives);
		damage.setDamageDealtToTurrets(damageDealtToTurrets);
		damage.setDamageSelfMitigated(damageSelfMitigated);
		damage.setTotalDamageDealt(totalDamageDealt);
		damage.setTotalDamageTaken(totalDamageTaken);
		
		Map<String, Double> damageTakenPerMinDeltas = participant.getTimeline().getDamageTakenPerMinDeltas();
		if(damageTakenPerMinDeltas != null) {
			Double damageTakenAt10 = damageTakenPerMinDeltas.get(KEY_0_10);
			Double damageTakenAt10To20 = damageTakenPerMinDeltas.get(KEY_10_20);
			Double damageTakenAt20To30 = damageTakenPerMinDeltas.get(KEY_20_30);

			damage.setDamageTakenAt10(damageTakenAt10);
			damage.setDamageTakenAt10To20(damageTakenAt10To20);
			damage.setDamageTakenAt20To30(damageTakenAt20To30);
		}
		
		return damage;
	}
}
