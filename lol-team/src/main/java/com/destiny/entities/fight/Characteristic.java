package com.destiny.entities.fight;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "characteristic")
public class Characteristic implements GenericEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	private int attack;
	private int strenght;
	private int defense;
	private int dexterity;
	private int dodge;
	private int reactivity;
	private int bravery;
	private int destiny;

	private int vitality;

	public Characteristic() {

	}

	public Characteristic(int strenght, int defense, int dexterity, int dodge, int reactivity, int vitality, int attack) {
		this.strenght = strenght;
		this.defense = defense;
		this.dexterity = dexterity;
		this.dodge = dodge;
		this.reactivity = reactivity;
		this.vitality = vitality;
		this.attack = attack;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getDexterity() {
		return dexterity;
	}

	public int getDodge() {
		return dodge;
	}

	public int getStrength() {
		return strenght;
	}

	public int getDefense() {
		return defense;
	}

	public int getReactivity() {
		return reactivity;
	}

	public void setReactivity(int reactivity) {
		this.reactivity = reactivity;
	}

	public int getBravery() {
		return bravery;
	}

	public void setBravery(int bravery) {
		this.bravery = bravery;
	}

	public int getDestiny() {
		return destiny;
	}

	public void setDestiny(int destiny) {
		this.destiny = destiny;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getVitality() {
		return vitality;
	}

	public void setVitality(int vitality) {
		this.vitality = vitality;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public void setStrength(int strength) {
		this.strenght = strength;
	}

	public void setDodge(int dodge) {
		this.dodge = dodge;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}
}