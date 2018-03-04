package com.lolteam.entities.match;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lolteam.entities.GenericEntity;

@Entity(name = "ExperienceEntity")
@Table(name = "experience")
public class ExperienceEntity implements GenericEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "xp_at_10")
	private Double xpAt10;

	@Column(name = "xp_at_10_to_20")
	private Double xpAt10To20;
	
	@Column(name = "xp_at_20_to_30")
	private Double xpAt20To30;
	
	@Column(name = "level_at_end")
	private int levelAtEnd;
	

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setExperienceAt10(Double xpAt10) {
		this.xpAt10 = xpAt10;
	}

	public Double getXpAt10() {
		return xpAt10;
	}

	public void setExperienceAt10To20(Double xpAt10To20) {
		this.xpAt10To20 = xpAt10To20;
	}

	public Double getXpAt10To20() {
		return xpAt10To20;
	}

	public void setExperienceAt20To30(Double xpAt20To30) {
		this.xpAt20To30 = xpAt20To30;
	}

	public Double getXpAt20To30() {
		return xpAt20To30;
	}

	public void setLevelAtEnd(int levelAtEnd) {
		this.levelAtEnd = levelAtEnd;
	}

	public int getLevelAtEnd() {
		return levelAtEnd;
	}

}
