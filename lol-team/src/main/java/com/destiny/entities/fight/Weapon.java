package com.destiny.entities.fight;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "weapon")
public class Weapon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "id")
	private Integer id;

	private String name;
	private int durability;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "weapon_bonuses_id")
	private WeaponBonuses weaponBonuses;

	//todo enum
	private String type;
	
	public Weapon() {
		
	}
	
	public Weapon(String name, String type, int durability,  WeaponBonuses weaponBonuses) {
		this.name=name;
		this.type = type;
		this.durability = durability;
		this.weaponBonuses = weaponBonuses;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

	public WeaponBonuses getWeaponBonuses() {
		return weaponBonuses;
	}

	public void setWeaponBonuses(WeaponBonuses weaponBonuses) {
		this.weaponBonuses = weaponBonuses;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
