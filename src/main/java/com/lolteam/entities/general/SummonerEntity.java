package com.lolteam.entities.general;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lolteam.jsonSerialiser.LocalDateDeserializer;
import com.lolteam.jsonSerialiser.LocalDateSerializer;

@Entity(name = "SummonerEntity")
@Table(name = "summoner")
@NamedQueries({
	@NamedQuery(name = "summoner.getSummonerEntityByAccountId", query = "SELECT s FROM SummonerEntity s WHERE s.accountId = :accountId"),
	@NamedQuery(name = "summoner.getSummonerEntityBySummonerName", query = "SELECT s FROM SummonerEntity s WHERE LOWER(s.name) = :summonerName"),
})
public class SummonerEntity implements GenericEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "id")
	private Long id;
	
	@Column(name="account_id", unique=true)
	private long accountId;
	
	@Column(unique=true)
	private String name;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@Column(name= "last_update")
	private LocalDate lastUpdate;
	
	@Column(name = "total_games")
	private Integer totalGames;

	@Column(name = "total_imported_games")
	private Integer totalImportedGames;
	
	@Override
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public LocalDate getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(LocalDate lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SummonerEntity) {
			SummonerEntity summonerEntity = (SummonerEntity) obj;
			return id == summonerEntity.id;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		if(id != null) {
			return (int) (id * 31);			
		}
		return 31;
	}

	public void setTotalGames(Integer totalGames) {
		this.totalGames = totalGames;
		
	}
	
	public Integer getTotalGames() {
		return totalGames;
	}

	public Integer getTotalImportedGames() {
		return totalImportedGames;
	}

	public void setTotalImportedGames(Integer totalImportedGames) {
		this.totalImportedGames = totalImportedGames;
	}


}
