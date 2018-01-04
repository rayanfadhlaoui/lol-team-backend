package com.destiny.entities.fight;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "experience")
@NamedQueries({
	@NamedQuery(name = "experience.getLeveledUpFighters", query = "SELECT "
			+ " f.id, f.characteristic, f.experience " 
			+ Fighter.FROM_QUERY 
			+ " WHERE "+
			" f.experience.nextLvlExp <= f.experience.currentExp"),
})
public class Experience implements GenericEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	private Integer level;
	
	@Column(name="next_lvl_exp")
	private Integer nextLvlExp;
	
	@Column(name="current_exp")
	private Integer currentExp;

	public Experience() {
	}

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getNextLvlExp() {
		return nextLvlExp;
	}

	public void setNextLvlExp(Integer nextLvlExp) {
		this.nextLvlExp = nextLvlExp;
	}

	public Integer getCurrentExp() {
		return currentExp;
	}

	public void setCurrentExp(Integer currentExp) {
		this.currentExp = currentExp;
	}
}
