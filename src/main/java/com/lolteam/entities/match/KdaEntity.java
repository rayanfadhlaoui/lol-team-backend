package com.lolteam.entities.match;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lolteam.entities.general.GenericEntity;

@Entity(name = "KdaEntity")
@Table(name = "kda")
public class KdaEntity implements GenericEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	private int kills;
	
	private int deaths;

	private int assists;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;		
	}
	
	public int getDeaths() {
		return deaths;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}
	
	public int getKills() {
		return kills;
	}

	public void setAssists(int assists) {
		this.assists = assists;
	}
	
	public int getAssists() {
		return assists;
	}

}
