package com.lolteam.entities.match;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lolteam.entities.GenericEntity;
import com.lolteam.entities.SummonerEntity;

@Entity(name = "ParticipantStatsEntity")
@Table(name = "participant_stats")
public class ParticipantStatsEntity implements GenericEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	private boolean winner;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "summoner_id")
	private SummonerEntity summoner;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "simple_stats_id")
	private SimpleStatsEntity simpleStatsEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "match_id")
	private MatchEntity matchEntity;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setSummonerEntity(SummonerEntity summonerEntity) {
		this.summoner = summonerEntity;
	}

	public void setSimpleStatsEntity(SimpleStatsEntity simpleStatsEntity) {
		this.simpleStatsEntity = simpleStatsEntity;
	}

	public void setMatchEntity(MatchEntity matchEntity) {
		this.matchEntity = matchEntity;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ParticipantStatsEntity) {
			ParticipantStatsEntity participantStatEntity = (ParticipantStatsEntity) obj;
			if(participantStatEntity.id != null && id != null) {
				return participantStatEntity.id.equals(id);
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		if(id != null) {
			return id.intValue() * 31;
		}
		return 31;
	}

	public boolean isWinner() {
		return winner;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}
	
}
