package com.lolteam.entities.match;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lolteam.entities.GenericEntity;

@Entity(name = "DamageEntity")
@Table(name = "damage")
public class DamageEntity implements GenericEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "damage_dealt_to_objectives")
	private long damageDealtToObjectives;
	
	@Column(name = "damage_dealt_to_turrets")
	private long damageDealtToTurrets;
	
	@Column(name = "damage_self_mitigated")
	private long damageSelfMitigated;
	
	@Column(name = "total_damage_dealt")
	private long totalDamageDealt;
	
	@Column(name = "total_damage_taken")
	private long totalDamageTaken;
	
	@Column(name = "damage_taken_at_10")
	private Double damageTakenAt10;
	
	@Column(name = "damage_taken_at_10_to_20")
	private Double damageTakenAt10To20;
	
	@Column(name = "damage_taken_at_20_to_30")
	private Double damageTakenAt20To30;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setDamageDealtToObjectives(long damageDealtToObjectives) {
		this.damageDealtToObjectives = damageDealtToObjectives;
	}

	public long getDamageDealtToObjectives() {
		return damageDealtToObjectives;
	}

	public void setDamageDealtToTurrets(long damageDealtToTurrets) {
		this.damageDealtToTurrets = damageDealtToTurrets;
	}
	
	public long getDamageDealtToTurrets() {
		return damageDealtToTurrets;
	}

	public void setDamageSelfMitigated(long damageSelfMitigated) {
		this.damageSelfMitigated = damageSelfMitigated;
	}
	
	public long getDamageSelfMitigated() {
		return damageSelfMitigated;
	}

	public void setTotalDamageDealt(long totalDamageDealt) {
		this.totalDamageDealt = totalDamageDealt;
	}
	
	public long getTotalDamageDealt() {
		return totalDamageDealt;
	}

	public void setTotalDamageTaken(long totalDamageTaken) {
		this.totalDamageTaken = totalDamageTaken;
	}
	
	public long getTotalDamageTaken() {
		return totalDamageTaken;
	}

	public void setDamageTakenAt10(Double damageTakenAt10) {
		this.damageTakenAt10 = damageTakenAt10;
	}
	
	public Double getDamageTakenAt10() {
		return damageTakenAt10;
	}

	public void setDamageTakenAt10To20(Double damageTakenAt10To20) {
		this.damageTakenAt10To20 = damageTakenAt10To20;
	}
	
	public Double getDamageTakenAt10To20() {
		return damageTakenAt10To20;
	}

	public void setDamageTakenAt20To30(Double damageTakenAt20To30) {
		this.damageTakenAt20To30 = damageTakenAt20To30;
	}
	
	public Double getDamageTakenAt20To30() {
		return damageTakenAt20To30;
	}
}
