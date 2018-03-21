package com.lolteam.entities.match;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lolteam.entities.general.GenericEntity;

/** Use for GameMode STARGUARDIAN, ODIN. */
@Entity(name = "ScoreEntity")
@Table(name = "score")
public class ScoreEntity implements GenericEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "combat_player")
	private Integer combatPlayer;

	@Column(name = "objective_player")
	private Integer objectivePlayer;

	@Column(name = "total_player")
	private Integer totalPlayer;

	@Column(name = "total_rank")
	private Integer totalRank;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setCombatPlayer(Integer combatPlayer) {
		this.combatPlayer = combatPlayer;
	}

	public Integer getCombatPlayer() {
		return combatPlayer;
	}

	public void setObjectivePlayer(Integer objectivePlayer) {
		this.objectivePlayer = objectivePlayer;
	}

	public Integer getObjectivePlayer() {
		return objectivePlayer;
	}

	public void setTotalPlayer(Integer totalPlayer) {
		this.totalPlayer = totalPlayer;
	}

	public Integer getTotalPlayer() {
		return totalPlayer;
	}

	public void setTotalRank(Integer totalRank) {
		this.totalRank = totalRank;
	}

	public Integer getTotalScoreRank() {
		return totalRank;
	}
}
