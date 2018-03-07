package com.lolteam.entities.match;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lolteam.entities.GenericEntity;

@Entity(name = "visionEntity")
@Table(name = "vision")
public class VisionEntity implements GenericEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name="wards_placed")
	private int wardsPlaced;

	@Column(name="wards_killed")
	private int wardsKilled;
	
	@Column(name="sigh_wards_bought")
	private int sightWardsBought;
	
	@Column(name="vision_score")
	private long visionScore;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setWardsPlaced(int wardsPlaced) {
		this.wardsPlaced = wardsPlaced;
	}
	
	public int getWardsPlaced() {
		return wardsPlaced;
	}

	public void setWardsKilled(int wardsKilled) {
		this.wardsKilled = wardsKilled;
	}

	public int getWardsKilled() {
		return wardsKilled;
	}
	
	public void setSightWardsBought(int sightWardsBought) {
		this.sightWardsBought = sightWardsBought;
	}
	
	public int getSightWardsBought() {
		return sightWardsBought;
	}

	public void setVisionScore(long visionScore) {
		this.visionScore = visionScore;
	}
	
	public long getVisionScore() {
		return visionScore;
	}
}
