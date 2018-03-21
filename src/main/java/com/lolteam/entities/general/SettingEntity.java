package com.lolteam.entities.general;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity(name = "SettingEntity")
@Table(name = "setting")
@NamedQueries({ 
	@NamedQuery(name = "SettingEntity.getRiotKey", query = "SELECT s.name FROM SettingEntity s WHERE  s.name = '" + SettingEntity.RIOT_KEY + "'"), })
public class SettingEntity implements GenericEntity {
	public static final String RIOT_KEY = "RiotKey";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	private String name;
	
	private String value;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
