package com.lolteam.entities.match;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.lolteam.entities.general.GenericEntity;
import com.lolteam.entities.match.GameMode;

@Entity(name = "MatchEntity")
@Table(name = "match")
@NamedQueries({	@NamedQuery(name = "match.getNbGamesImported", query = "SELECT count(p) FROM ParticipantStatsEntity p WHERE p.summoner.id = :summonerId"),
				@NamedQuery(name = "match.getGameIdImported", query = "SELECT m.gameId FROM MatchEntity m "
						+ "inner join m.winningParticipantStats wp " 
						+ "inner join m.losingParticipantStats lp " 
						+ "WHERE lp.summoner.id = :summonerId OR wp.summoner.id = :summonerId"), })
public class MatchEntity implements GenericEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "game_id", unique = true)
	private long gameId;

	@Column(name = "game_duration")
	private long gameDuration;

	@Column(name = "game_mode")
	@Enumerated(EnumType.STRING)
	private GameMode gameMode;

	@Column(name = "game_version")
	private String gameVersion;

	@Column(name = "game_type")
	private String gameType;	
	
	@Enumerated(EnumType.STRING)
	private QueueEnum queue;
	
	@OneToMany(mappedBy = "matchEntity", cascade = CascadeType.ALL, orphanRemoval = true)
	@Where(clause = "winner = 'true'")
	private Set<ParticipantStatsEntity> winningParticipantStats = new HashSet<>();

	@OneToMany(mappedBy = "matchEntity", cascade = CascadeType.ALL, orphanRemoval = true)
	@Where(clause = "winner = 'false'")
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
		participantStatsEntity.setWinner(true);
		participantStatsEntity.setMatchEntity(this);
	}

	public void addloser(ParticipantStatsEntity participantStatsEntity) {
		participantStatsEntity.setWinner(false);
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

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
	
	public String getGameType() {
		return gameType;
	}

	public void setQueue(QueueEnum queue) {
		this.queue = queue;
	}
	
	public QueueEnum getQueue() {
		return queue;
	}
}
