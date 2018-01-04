package com.destiny.entities.fight;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "weapon_bonuses")
public class WeaponBonuses {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name="knockout_bonus")
	private int knockoutBonus;
	
	@Column(name="strike_bonus")
	private int strikeBonus;
	
	@Column(name = "ko_probability")
	private int koProbability;

	@Column(name = "broken_bone_probability")
	private int brokenBoneProbability;

	@Column(name = "amputeed_member_probability")
	private int amputeedMemberProbability;

	@Column(name = "death_probability")
	private int deathProbability;

	public WeaponBonuses() {
	}

	public WeaponBonuses(int knockoutBonus, int strikeBonus, int koProbability, int brokenBoneProbability,
			int amputeedMemberProbability, int deathProbability) {
		this.strikeBonus = strikeBonus;
		this.knockoutBonus = knockoutBonus;
		this.koProbability = koProbability;
		this.brokenBoneProbability=brokenBoneProbability;
		this.amputeedMemberProbability=amputeedMemberProbability;
		this.deathProbability=deathProbability;
	}

	public int getKnockoutBonus() {
		return knockoutBonus;
	}

	public void setKnockoutBonus(int knockoutBonus) {
		this.knockoutBonus = knockoutBonus;
	}

	public int getKoProbability() {
		return koProbability;
	}

	public void setKoProbability(int koProbability) {
		this.koProbability = koProbability;
	}

	public int getBrokenBoneProbability() {
		return brokenBoneProbability;
	}

	public void setBrokenBoneProbability(int brokenBoneProbability) {
		this.brokenBoneProbability = brokenBoneProbability;
	}

	public int getAmputeedMemberProbability() {
		return amputeedMemberProbability;
	}

	public void setAmputeedMemberProbability(int amputeedMemberProbability) {
		this.amputeedMemberProbability = amputeedMemberProbability;
	}

	public int getDeathProbability() {
		return deathProbability;
	}

	public void setDeathProbability(int deathProbability) {
		this.deathProbability = deathProbability;
	}

	public int getStrikeBonus() {
		return strikeBonus;
	}

	public void setStrikeBonus(int strikeBonus) {
		this.strikeBonus = strikeBonus;
	}
}
