package com.lolteam.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.lolteam.entities.match.GameMode;

@Entity(name = "MatchEntity")
@Table(name = "match")
public class MatchEntity implements GenericEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "game_id")
	private long gameId;

	@Column(name = "game_duration")
	private long gameDuration;

	@Column(name = "game_mode")
	@Enumerated(EnumType.STRING)
	private GameMode gameMode;

	@Column(name = "game_version")
	private String gameVersion;

	@OneToMany(mappedBy = "matchEntity", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ParticipantStatsEntity> winningParticipantStats = new HashSet<>();

	@OneToMany(mappedBy = "matchEntity", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ParticipantStatsEntity> losingParticipantStats = new HashSet<>();

	public MatchEntity() {

	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public void setGameDuration(long gameDuration) {
		this.gameDuration = gameDuration;
	}

	public long getGameDuration() {
		return gameDuration;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public void setGameVersion(String gameVersion) {
		this.gameVersion = gameVersion;
	}

	public void addWinner(ParticipantStatsEntity participantStatsEntity) {
		winningParticipantStats.add(participantStatsEntity);
		participantStatsEntity.setMatchEntity(this);
	}

	public void addloser(ParticipantStatsEntity participantStatsEntity) {
		losingParticipantStats.add(participantStatsEntity);
		participantStatsEntity.setMatchEntity(this);
	}
	
	public void removeWinner(ParticipantStatsEntity participantStatsEntity) {
		winningParticipantStats.remove(participantStatsEntity);
		participantStatsEntity.setMatchEntity(null);
	}
	
	public void removeLoser(ParticipantStatsEntity participantStatsEntity) {
		losingParticipantStats.remove(participantStatsEntity);
		participantStatsEntity.setMatchEntity(null);
	}

}
