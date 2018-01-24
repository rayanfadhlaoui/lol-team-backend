package com.lolteam.entities;

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

@Entity(name = "ParticipantStatsEntity")
@Table(name = "participant_stats")
public class ParticipantStatsEntity implements GenericEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "summoner_id")
	private SummonerEntity summonerEntity;

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
		this.summonerEntity = summonerEntity;
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
	
}
