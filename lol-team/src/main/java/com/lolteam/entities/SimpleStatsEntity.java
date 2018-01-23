package com.lolteam.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "SimpleStatsEntity")
@Table(name = "simple_stats")
public class SimpleStatsEntity implements GenericEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "champion_id")
	private ChampionEntity championEntity;

	private String role;
	private String lane;

	@Column(name="farm_at_10")
	private Double farmAt10;

	@Column(name="farm_at_10_to_20")
	private Double farmAt10To20;

	@Column(name="farm_at_20_to_30")
	private Double farmAt20To30;

	public ChampionEntity getChampionEntity() {
		return championEntity;
	}

	public String getLane() {
		return lane;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Double getFarmAt10() {
		return farmAt10;
	}

	public Double getFarmAt10To20() {
		return farmAt10To20;
	}

	public Double getFarmAt20To30() {
		return farmAt20To30;
	}

	public void setChampionEntity(ChampionEntity championEntity) {
		this.championEntity = championEntity;

	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setLane(String lane) {
		this.lane = lane;
	}

	public void setFarmAt10(Double farmAt10) {
		this.farmAt10 = farmAt10;
	}

	public void setFarmAt10To20(Double farmAt10To20) {
		this.farmAt10To20 = farmAt10To20;
	}

	public void setFarmAt20To30(Double farmAt20To30) {
		this.farmAt20To30 = farmAt20To30;
	}
}
