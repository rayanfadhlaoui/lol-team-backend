package com.destiny.entities.fight;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.destiny.entities.fight.Weapon;
import com.destiny.entities.fight.Characteristic;
import com.destiny.entities.general.Person;

@Entity
@Table(name = "fighter")
@NamedQueries({
	@NamedQuery(name = "fighter.getFighterByRace", query = "SELECT f" + Fighter.FROM_QUERY + " WHERE " + Fighter.RACE_FILTER),
	@NamedQuery(name = "fighter.getFighterByRaceAndClasse", query = "SELECT f" + Fighter.FROM_QUERY + " WHERE " + Fighter.RACE_FILTER + " AND " + Fighter.CLASSE_FILTER),
	@NamedQuery(name = "fighter.getFighterByRace.count", query = "SELECT COUNT(f)" + Fighter.FROM_QUERY + " WHERE " + Fighter.RACE_FILTER)
})
public class Fighter implements GenericEntity {
	
	static final String CLASSE_FILTER = " f.person.title = :title";
	static final String RACE_FILTER = " f.person.race = :race";
	static final String FROM_QUERY = " FROM Fighter f";

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "person_id")
	private Person person;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "characteristic_id")
	private Characteristic characteristic;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "state_id")
	private State state;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "weapon_id")
	private Weapon weapon;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "history_id")
	private History history;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "experience_id")
	private Experience experience;
	
	public Fighter() {

	}
	
	public Fighter(Person person) {
		this.person = person;
	}

	public Fighter(Person person, Characteristic characteristic, State state) {
		this.person = person;
		this.characteristic = characteristic;
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Characteristic getCharacteristic() {
		return characteristic;
	}

	public State getState() {
		return state;
	}

	public Person getPerson() {
		return person;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setCharacteristic(Characteristic characteristic) {
		this.characteristic = characteristic;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return getPerson().toString();
	}

	public History getHistory() {
		return history;
	}

	public void setHistory(History history) {
		this.history = history;
	}
	
	public void setExperience(Experience experience) {
		this.experience = experience;
	}

	public Experience getExperience() {
		return this.experience;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Fighter) {
			Fighter fighter = (Fighter) o;
			return fighter.getId().equals(id);
		}
		
		return false;
	}
}
