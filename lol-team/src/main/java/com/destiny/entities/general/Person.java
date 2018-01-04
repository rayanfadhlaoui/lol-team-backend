package com.destiny.entities.general;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.destiny.entities.fight.GenericEntity;
import com.destiny.entities.fight.TitleEnum;
import com.destiny.entities.fight.Town;

@Entity
@Table(name = "person")
public class Person implements GenericEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "id")
	private Integer id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "birthdate")
	private Date birthDate;

	//todo enum
	private String race;
	
	@Enumerated(EnumType.STRING)
	private TitleEnum title;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "hometown_id")
	private	Town homeTown;

	public Person() {
	}

	public Person(String firstName, String lastName, String race, Date birthDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.race = race;
		this.birthDate = birthDate;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return firstName;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public Town getHomeTown() {
		return homeTown;
	}

	public void setHomeTown(Town homeTown) {
		this.homeTown = homeTown;
	}

	public TitleEnum getTitle() {
		return title;
	}

	public void setTitle(TitleEnum title) {
		this.title = title;
	}

}
