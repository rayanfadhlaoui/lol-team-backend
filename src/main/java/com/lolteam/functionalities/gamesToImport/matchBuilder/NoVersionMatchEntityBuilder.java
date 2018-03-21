package com.lolteam.functionalities.gamesToImport.matchBuilder;

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

class NoVersionMatchEntityBuilder extends AbstractMatchEntityBuilder {

	NoVersionMatchEntityBuilder(Match match, EntityCache<Long, SummonerEntity> summonerEntityCache,
			EntityCache<Integer, ChampionEntity> championEntityCache) {
		super(match, summonerEntityCache, championEntityCache);
	}

	@Override
	protected void extracOtherDataFromTimeLine(ParticipantTimeline timeline, SimpleStatsEntity simpleStatsEntity) {
		simpleStatsEntity.setLane("UNKNOWN");
		simpleStatsEntity.setRole("UNKNOWN");
	}

	@Override
	void setVersion() {
		matchEntity.setGameVersion("UNKNOWN");
	}

	@Override
	protected FarmEntity extractFarm(Participant participant) {
		FarmEntity farmEntity = new FarmEntity();

		int totalFarm = participant.getStats()
				.getTotalMinionsKilled();
		double farmAtEnd = totalFarm / match.getGameDuration();
		farmEntity.setFarmAtEnd(farmAtEnd);
		return farmEntity;
	}

	@Override
	protected GoldEntity extractGoldStats(Participant participant) {
		ParticipantStats stats = participant.getStats();
		GoldEntity gold = new GoldEntity();
		gold.setGoldAtEnd(stats.getGoldEarned());
		gold.setGoldSpent(stats.getGoldSpent());
		return gold;
	}

	@Override
	protected ExperienceEntity extractExperience(Participant participant) {
		ExperienceEntity experience = new ExperienceEntity();
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
		
		return damage;
	}

}
