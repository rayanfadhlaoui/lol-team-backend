package com.lolteam.entities.match;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lolteam.entities.general.GenericEntity;

@Entity(name = "GoldEntity")
@Table(name = "gold")
public class GoldEntity implements GenericEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "gold_spent")
	private int goldSpent;

	@Column(name = "gold_at_10")
	private Double goldAt10;

	@Column(name = "gold_at_10_to_20")
	private Double goldAt10To20;

	@Column(name = "gold_at_20_to_30")
	private Double goldAt20To30;

	@Column(name = "gold_at_end")
	private int goldAtEnd;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setGoldAtEnd(int goldAtEnd) {
		this.goldAtEnd = goldAtEnd;
	}
	
	public int getGoldAtEnd() {
		return goldAtEnd;
	}

	public void setGoldSpent(int goldSpent) {
		this.goldSpent = goldSpent;
	}
	
	public int getGoldSpent() {
		return goldSpent;
	}

	public void setGoldAt10(Double goldAt10) {
		this.goldAt10 = goldAt10;
	}
	
	public Double getGoldAt10() {
		return goldAt10;
	}

	public void setGoldAt10To20(Double goldAt10To20) {
		this.goldAt10To20 = goldAt10To20;
	}
	
	public Double getGoldAt10To20() {
		return goldAt10To20;
	}

	public void setGoldAt20To30(Double goldAt20To30) {
		this.goldAt20To30 = goldAt20To30;
	}
	
	public Double getGoldAt20To30() {
		return goldAt20To30;
	}
}
