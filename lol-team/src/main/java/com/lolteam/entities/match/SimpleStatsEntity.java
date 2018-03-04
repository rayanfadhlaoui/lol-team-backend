package com.lolteam.entities.match;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lolteam.entities.ChampionEntity;
import com.lolteam.entities.GenericEntity;

@Entity(name = "SimpleStatsEntity")
@Table(name = "simple_stats")
public class SimpleStatsEntity implements GenericEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "champion_id")
	private ChampionEntity championEntity;

	private String role;
	
	private String lane;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "farm_id")
	private FarmEntity farm;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vision_id")
	private VisionEntity vision;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "kda_id")
	private KdaEntity kda;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "score_id")
	private ScoreEntity score;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gold_id")
	private GoldEntity gold;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "experience_id")
	private ExperienceEntity experience;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "damage_id")
	private DamageEntity damage;

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

	public void setVision(VisionEntity vision) {
		this.vision = vision;
	}
	
	public VisionEntity getVision() {
		return vision;
	}

	public KdaEntity getKda() {
		return kda;
	}

	public void setKda(KdaEntity kda) {
		this.kda = kda;
	}

	public FarmEntity getFarm() {
		return farm;
	}

	public void setFarm(FarmEntity farm) {
		this.farm = farm;
	}

	public void setScore(ScoreEntity score) {
		this.score = score;
	}

	public ScoreEntity getScore() {
		return score;
	}

	public void setGold(GoldEntity gold) {
		this.gold = gold;
	}
	
	public GoldEntity getGold() {
		return gold;
	}

	public void setExperience(ExperienceEntity experience) {
		this.experience = experience;
	}
	
	public ExperienceEntity getExperience() {
		return experience;
	}

	public void setDamage(DamageEntity damage) {
		this.damage = damage;
	}
	
	public DamageEntity getDamage() {
		return damage;
	}
}
