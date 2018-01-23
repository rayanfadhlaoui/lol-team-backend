package com.lolteam.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity(name = "LolTeamUserEntity")
@Table(name = "lt_user")
@NamedQueries({
                @NamedQuery(name = "ltUser.getUserFromloginAndPassword", query = "SELECT u FROM LolTeamUserEntity u WHERE  u.username = :username AND u.password = :password"),
})
public class LolTeamUserEntity implements GenericEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	private String username;

	private String password;

	public LolTeamUserEntity() {

	}

	public LolTeamUserEntity(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
